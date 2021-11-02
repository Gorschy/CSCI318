package Customer;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class CustomerController {
    @Autowired
    CustomerService service;

    // find all customers in system 
    @GetMapping("/customers")
    List<Customer> all(){
        return service.all();
    }

    // find one customer in system by id
    @GetMapping("/customers/{id}")
    Customer one(@PathVariable Long id){
        return service.one(id);
    }

    // create a new customer
    @PostMapping("/customers")
    Customer newCustomer(@RequestBody Customer newCustomer){
        return service.newCustomer(newCustomer);
    }

    // update an existing custoemr
    @PutMapping("/customers/{id}")
    Customer replaceCustomer(@RequestBody Customer newCustomer, @PathVariable Long id){
        return service.replaceCustomer(id, newCustomer);
    }

    // delete a customer based on id
    @DeleteMapping("/customers/{id}")
    void deleteCustomer(@PathVariable Long id){
        service.deleteCustomer(id);
    }

    // join a contact object to a customer object
    @PutMapping("/customers/{custId}/contact/{contId}")
    Customer joinContactCustomer(@PathVariable("custId") Long custId, @PathVariable("contId") Long contId){
        return service.joinCustomerContact(custId, contId);
    }
}