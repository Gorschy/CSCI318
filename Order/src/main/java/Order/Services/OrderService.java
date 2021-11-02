package OrderEntity;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
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
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    RestTemplate restTemplate;

    // Find a customer by its id
    Customer getCustomerById(Long id) {
        Customer customer = restTemplate.getForObject("http://localhost:8081/customers/" + Long.toString(id), Customer.class);
        return customer;
    }
    // Find a product by its id
    Product getProductById(Long id) {
        Product product = restTemplate.getForObject("http://localhost:8082/products/" + Long.toString(id), Product.class);
        return product;
    }

    // Get list of all customers
    List<Customer> getCustomers() {
        List<Customer> customers = restTemplate.getForObject("http://localhost:8081/customers", List<Customer>.class);
        return customers;
    }
    
    // find all orders in the system and return a list of orders
    List<OrderEntity> all() {
        return orderRepository.findAll();
    }

    // Find a specific order by id
    OrderEntity one(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
    }

    // Create a new order
    OrderEntity newOrder(OrderEntity newOrder) {
        return orderRepository.save(newOrder);
    }

    // Update an existing order
    OrderEntity replaceOrder(Long id, OrderEntity newOrder) {
        return orderRepository.findById(id).map(order -> {
            order.setCustId(newOrder.getCustId());
            order.setProdId(newOrder.getProdId());
            order.setProdId(newOrder.getProdId());
            return orderRepository.save(order);
        }).orElseGet(() -> {
            newOrder.setId(id);
            return orderRepository.save(newOrder);
        });
    }

    // Delete a order entity based on id
    void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}