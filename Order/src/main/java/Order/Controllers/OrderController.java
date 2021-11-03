package OrderEntity;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
class OrderController {

    @Autowired
    OrderService service;

    // methods to search through the orders database
    @GetMapping("/orders")
    public List<OrderEntity> all() {
        return service.all();
    }
    @GetMapping("/orders/{id}")
    public OrderEntity one(@PathVariable Long id) {
        return service.one(id);
    }

    // methods to get information from an order
    @GetMapping("/orders/{id}/customer")
    public Customer findCustomer(@PathVariable Long id) {
        return service.getCustomerById(id);
    }
    @GetMapping("/orders/{id}/product")
    public Product findProduct(@PathVariable Long id) {
        return service.getProductById(id);
    }

    //methods to create/update/delete entries
    @PostMapping("/orders")
    public OrderEntity newOrder(
        @RequestBody OrderEntity newOrder) {
        if(newOrder.getQuantity() > service.getProductById(newOrder.getProdId()).getStockQuantity())
            throw new OrderBadRequestException("Unable to process order. Requested more units than avaible in stock.");
        
        return service.newOrder(newOrder);
    }
    @PutMapping("/orders/{id}")
    OrderEntity replaceOrder(
        @PathVariable Long id,
        @RequestBody OrderEntity newOrder
    ) {
        if(newOrder.getQuantity() > service.getProductById(newOrder.getProdId()).getStockQuantity())
            throw new OrderBadRequestException("Unable to process order. Requested more units than avaible in stock.");
        
        return service.replaceOrder(id, newOrder);
    }
    @DeleteMapping("/orders/{id}")
    void deleteOrder(@PathVariable Long id) {
        service.deleteOrder(id);
    }

    //create a random object
    @GetMapping("/orders/random")
    OrderEntity createRandomOrder() {
        return service.createRandomOrder();
    }
}