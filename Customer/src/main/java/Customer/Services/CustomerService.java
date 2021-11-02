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
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository custRepository;
    @Autowired
    private ContactRepository contRepository;

    // find all customers in the system and return a list of customers
    List<Customer> all() {
        return custRepository.findAll();
    }

    // Find a specific customer by id
    Customer one(Long id) {
        return custRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
    }

    // Create a new customer
    Customer newCustomer(Customer newCustomer) {
        return custRepository.save(newCustomer);
    }

    // Update an existing customer
    Customer replaceCustomer(Long id, Customer newCustomer) {
        return custRepository.findById(id).map(customer -> {
            customer.setCompanyName(newCustomer.getCompanyName());
            customer.setAddress(newCustomer.getAddress());
            customer.setCountry(newCustomer.getCountry());
            customer.setContact(newCustomer.getContact());
            return custRepository.save(customer);
        }).orElseGet(() -> {
            newCustomer.setId(id);
            return custRepository.save(newCustomer);
        });
    }

    // Delete a customer entity based on id
    void deleteCustomer(Long id) {
        custRepository.deleteById(id);
    }

    //join a contact object to a customer object
    Customer joinCustomerContact(Long custId, Long contId) {
        Customer customer = custRepository.findById(custId).orElseThrow(() -> new CustomerNotFoundException(custId));
        Contact contact = contRepository.findById(contId).orElseThrow(() -> new CustomerNotFoundException(contId));

        customer.setContact(contact);
        return custRepository.save(customer);
    }
}