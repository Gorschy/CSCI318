package com.example.demo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerContactRepository;
import com.example.demo.repository.CustomerRepository;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class CustomerController {
    private final CustomerRepository custRepository;
    private final CustomerContactRepository contRepository;
    private final CustomerModelAssembler custAssembler;
    private final CustomerContactModelAssembler contAssmbler;

    CustomerController(CustomerRepository custRepository, CustomerContactRepository contRepository, CustomerModelAssembler custAssembler, CustomerContactModelAssembler contAssmbler) {
        this.custRepository = custRepository;
        this.custAssembler = custAssembler;
        this.contRepository = contRepository;
        this.contAssmbler = contAssmbler;
    }

    @GetMapping("/customers")
    CollectionModel<EntityModel<Customer>> all(){
        List<EntityModel<Customer>> customers = custRepository.findAll().stream().map(custAssembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(customers,
            linkTo(methodOn(CustomerController.class).all()).withSelfRel());
    }

    @GetMapping("/customers/{id}")
    EntityModel<Customer> one(@PathVariable Long id){
        Customer customer = custRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));

        return custAssembler.toModel(customer);
    }

    @PostMapping("/customers")
    ResponseEntity<?> newCustomer(@RequestBody Customer newCustomer){
        EntityModel<Customer> entityModel = custAssembler.toModel(custRepository.save(newCustomer));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping("/customers/{id}")
    ResponseEntity<?> replaceCustomer(@RequestBody Customer newCustomer, @PathVariable Long id){
        Customer updatedCustomer = custRepository.findById(id).map(customer -> {
            customer.setCompanyName(newCustomer.getCompanyName());
            customer.setAddress(newCustomer.getAddress());
            customer.setCountry(newCustomer.getCountry());
            customer.setCustomerContact(newCustomer.getCustomerContact());
            return custRepository.save(customer);
        }).orElseGet(() -> {
            newCustomer.setId(id);
            return custRepository.save(newCustomer);
        });

        EntityModel<Customer> entityModel = custAssembler.toModel(updatedCustomer);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/customers/{id}")
    ResponseEntity<?> deleteCustomer(@PathVariable Long id){
        custRepository.deleteById(id);
        
        return ResponseEntity.noContent().build();
    }

}