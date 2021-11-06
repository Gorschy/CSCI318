package OrderEntity;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.Random;
import java.util.HashSet;
import java.util.HashSet.*;
import com.fasterxml.jackson.core.type.TypeReference;

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
    Customer[] getCustomers() {
        /*
        CustomerWrapper response = restTemplate.getForObject("http://localhost:8081/customers", CustomerWrapper.class);
        List<Customer> customers = response.getCustomers();
        return customers;*/

        ResponseEntity<Customer[]> response = restTemplate.getForEntity("http://localhost:8081/customers",Customer[].class);
        Customer[] customers = response.getBody();
        return customers;
}
    // Get list of all products
    Product[] getProducts() {
        /*
        ProductWrapper response = restTemplate.getForObject("http://localhost:8082/products", ProductWrapper.class);
        List<Product> products = response.getProducts();
        return products;*/

        ResponseEntity<Product[]> response = restTemplate.getForEntity("http://localhost:8082/products",Product[].class);
        Product[] products = response.getBody();
        return products;
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

    //create a random order entity using existing customers and products
    OrderEntity createRandomOrder() {
        Random rand = new Random();
        
        Customer[] customers = getCustomers();
        Customer customer = customers[rand.nextInt(customers.length)];

        Product[] products = getProducts();
        Product product = products[rand.nextInt(products.length)];

        Long quantity = Math.abs(1 + ((long)rand.nextLong()%(product.getStockQuantity()-1)));

        OrderEntity order = new OrderEntity(customer.getId(), product.getId(), quantity);

        return orderRepository.save(order);
    }

    // get customer total value and product list
    List<OrderEntity> findCustomerOrders(Long custId) {
        return orderRepository.findByCustId(custId);
    }

    List<Product> ordersToProducts(List<OrderEntity> orders) {
        List<Product> products = new ArrayList<>();
        for(OrderEntity o : orders){
            Product p = getProductById(o.getProdId());
            products.add(p);
        }
        return products;
    }

    double getValueOrder(OrderEntity order) {
        Long quantity = order.getQuantity();
        double singlePrice = getProductById(order.getProdId()).getPrice();
        return quantity * singlePrice;
    }

    CustomerValue findCustomerValue(Long custId) {
        List<OrderEntity> orders = findCustomerOrders(custId);
        
        double value = 0;
        for(OrderEntity o : orders)
            value += getValueOrder(o);

        List<Product> products = ordersToProducts(orders);
        HashSet<Product> productsSet = new HashSet<>(products);
        products.clear();
        products.addAll(productsSet);

        CustomerValue customer = new CustomerValue(products, value);
        return customer;
    }


    // loop through all products, add them to a list of ordered products if they have been ordered 
    List<Product> findOrderedProducts() {
        List<Product> ordered = new ArrayList<>();
        for(Product p : getProducts()) {
            if(hasBeenOrdered(p.getId()))
                ordered.add(p);
        }
        return ordered;
    }

    boolean hasBeenOrdered(Long prodId) {
        if(orderRepository.findByProdId(prodId).size() > 0)
            return true;
        return false;
    }

    Long findQuantityOrdered(Long prodId) {
        List<OrderEntity> orders = orderRepository.findByProdId(prodId);
        Long quantity = 0l;

        for(OrderEntity o : orders)
            quantity += o.getQuantity();

        return quantity;
    }

    List<OrderedQuantity> findOrderedQuantity() {
        List<OrderedQuantity> oq = new ArrayList<>();
        List<Product> ordered = findOrderedProducts();
        for(Product p : ordered) {
            Long q = findQuantityOrdered(p.getId());
            OrderedQuantity temp = new OrderedQuantity(p, q);
            oq.add(temp);
        }
        return oq;
    }
}