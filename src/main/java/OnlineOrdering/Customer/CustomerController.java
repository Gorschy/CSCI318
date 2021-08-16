package OnlineOrdering;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

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
    private final CustomerRepository repository;
    private final CustomerModelAssembler assembler;

    CustomerController(CustomerRepository repository, CustomerModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @GetMapping("/customers")
    CollectionModel<EntityModel<Customer>> all(){
        List<EntityModel<Customer>> customers = repository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(customers,
            linkTo(methodOn(CustomerController.class).all()).withSelfRel());
    }

    @GetMapping("/customers/{id}")
    EntityModel<Customer> one(@PathVariable Long id){
        Customer customer = repository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));

        return assembler.toModel(customer);
    }

    @PostMapping("/customers")
    ResponseEntity<?> newCustomer(@RequestBody Customer newCustomer){
        EntityModel<Customer> entityModel = assembler.toModel(repository.save(newCustomer));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @PutMapping("/customers/{id}")
    ResponseEntity<?> replaceCustomer(@RequestBody Customer newCustomer, @PathVariable Long id){
        Customer updatedCustomer = repository.findById(id).map(customer -> {
            customer.setCompanyName(newCustomer.getCompanyName());
            customer.setAddress(newCustomer.getAddress());
            customer.setCountry(newCustomer.getCountry());
            //customer.setContact(newCustomer.setContact());
            return repository.save(customer);
        }).orElseGet(() -> {
            newCustomer.setId(id);
            return repository.save(newCustomer);
        });

        EntityModel<Customer> entityModel = assembler.toModel(updatedCustomer);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    @DeleteMapping("/customers/{id}")
    ResponseEntity<?> deleteCustomer(@PathVariable Long id){
        repository.deleteById(id);
        
        return ResponseEntity.noContent().build();
    }

}