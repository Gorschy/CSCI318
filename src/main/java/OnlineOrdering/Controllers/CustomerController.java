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
    private final CustomerRepository custRepository;
    private final ContactRepository contRepository;
    private final CustomerModelAssembler custAssembler;
    private final ContactModelAssembler contAssmbler;

    CustomerController(CustomerRepository custRepository, ContactRepository contRepository, CustomerModelAssembler custAssembler, ContactModelAssembler contAssmbler) {
        this.custRepository = custRepository;
        this.custAssembler = custAssembler;
        this.contRepository = contRepository;
        this.contAssmbler = contAssmbler;
    }

    // find all customers in system 
    @GetMapping("/customers")
    CollectionModel<EntityModel<Customer>> all(){
        List<EntityModel<Customer>> customers = custRepository.findAll().stream().map(custAssembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(customers,
            linkTo(methodOn(CustomerController.class).all()).withSelfRel());
    }

    // find one customer in system by id
    @GetMapping("/customers/{id}")
    EntityModel<Customer> one(@PathVariable Long id){
        Customer customer = custRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));

        return custAssembler.toModel(customer);
    }

    // create a new customer without a contact
    @PostMapping("/customer/{companyName}/{address}/{country}") 
    ResponseEntity<?> newCustomer(
        @PathVariable String companyName,
        @PathVariable String address,
        @PathVariable String country
    ) {
        Customer newCustomer = new Customer(companyName, address, country);

        EntityModel<Customer> entityModel = custAssembler.toModel(custRepository.save(newCustomer));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
    // create a new customer with a contact
    @PostMapping("/customer/{companyName}/{address}/{country}/{contId}") 
    ResponseEntity<?> newCustomer(
        @PathVariable String companyName,
        @PathVariable String address,
        @PathVariable String country,
        @PathVariable Long contId
        
    ) {
        Contact contact = contRepository.findById(contId).orElseThrow(() -> new ContactNotFoundException(contId));
        Customer newCustomer = new Customer(companyName, address, country);

        newCustomer.setContact(contact);

        EntityModel<Customer> entityModel = custAssembler.toModel(custRepository.save(newCustomer));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    // update an existing customer without a contact
    @PutMapping("/customer/{custId}/{companyName}/{address}/{country}") 
    ResponseEntity<?> updateCustomer(
        @PathVariable Long custId,
        @PathVariable String companyName,
        @PathVariable String address,
        @PathVariable String country
    ) {
        Customer newCustomer = new Customer(companyName, address, country);

        Customer updatedCustomer = custRepository.findById(custId).map(customer -> {
            customer.setCompanyName(newCustomer.getCompanyName());
            customer.setAddress(newCustomer.getAddress());
            customer.setCountry(newCustomer.getCountry());
            customer.setContact(newCustomer.getContact());
            return custRepository.save(customer);
        }).orElseGet(() -> {
            newCustomer.setId(custId);
            return custRepository.save(newCustomer);
        });

        EntityModel<Customer> entityModel = custAssembler.toModel(updatedCustomer);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
    // update an existing customer with a contact
    @PutMapping("/customer/{custId}/{companyName}/{address}/{country}/{contId}") 
    ResponseEntity<?> updateCustomer(
        @PathVariable Long custId,
        @PathVariable String companyName,
        @PathVariable String address,
        @PathVariable String country,
        @PathVariable Long contId
    ) {
        Contact contact = contRepository.findById(contId).orElseThrow(() -> new ContactNotFoundException(contId));
        Customer newCustomer = new Customer(companyName, address, country);

        newCustomer.setContact(contact);

        Customer updatedCustomer = custRepository.findById(custId).map(customer -> {
            customer.setCompanyName(newCustomer.getCompanyName());
            customer.setAddress(newCustomer.getAddress());
            customer.setCountry(newCustomer.getCountry());
            customer.setContact(newCustomer.getContact());
            return custRepository.save(customer);
        }).orElseGet(() -> {
            newCustomer.setId(custId);
            return custRepository.save(newCustomer);
        });

        EntityModel<Customer> entityModel = custAssembler.toModel(updatedCustomer);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }


    // delete a customer based on id
    @DeleteMapping("/customers/{id}")
    ResponseEntity<?> deleteCustomer(@PathVariable Long id){
        custRepository.deleteById(id);
        
        return ResponseEntity.noContent().build();
    }

    // join a contact object to a customer object
    @PutMapping("/customers/{custId}/contact/{contId}")
    ResponseEntity<?> joinContactCustomer(@PathVariable("custId") Long custId, @PathVariable("contId") Long contId){
        Customer customer = custRepository.findById(custId).orElseThrow(() -> new CustomerNotFoundException(custId));
        Contact contact = contRepository.findById(contId).orElseThrow(() -> new ContactNotFoundException(contId));

        customer.setContact(contact);
        custRepository.save(customer);
        EntityModel<Customer> entityModel = custAssembler.toModel(customer);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
}