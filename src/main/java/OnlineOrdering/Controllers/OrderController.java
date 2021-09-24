package OnlineOrdering.Controllers;
import OnlineOrdering.Models.*;
import OnlineOrdering.ModelAssemblers.*;
import OnlineOrdering.Repositories.*;
import OnlineOrdering.ErrorHandling.*;

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
public class OrderController {
    //repositories required
    private final OrderRepository oRepository;
    private final CustomerRepository cRepository;
    private final ProductRepository pRepository;
    //model assemblers required
    private final OrderModelAssembler oModelAssembler;
    private final CustomerModelAssembler cModelAssembler;
    private final ProductModelAssembler pModelAssembler;

    OrderController(
        OrderRepository oRepository,
        CustomerRepository cRepository,
        ProductRepository pRepository,
        OrderModelAssembler oModelAssembler,
        CustomerModelAssembler cModelAssembler,
        ProductModelAssembler pModelAssembler
    ) {
        this.oRepository = oRepository;
        this.cRepository = cRepository;
        this.pRepository = pRepository;

        this.oModelAssembler = oModelAssembler;
        this.cModelAssembler = cModelAssembler;
        this.pModelAssembler = pModelAssembler;
    }

    //find all orders currently in the system
    @GetMapping("/orders")
    public CollectionModel<EntityModel<OrderEnt>> all() {
        List<EntityModel<OrderEnt>> orders = oRepository.findAll().stream().map(oModelAssembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(orders,
            linkTo(methodOn(OrderController.class).all()).withSelfRel());
    }

    //find one order by orderId
    @GetMapping("/order/{id}")
    public EntityModel<OrderEnt> one(@PathVariable("id") Long id) {
        OrderEnt order = oRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        
        return oModelAssembler.toModel(order);
    }

    //get customer info from order
    @GetMapping("/order/{id}/customer")
    EntityModel<Customer> findCustumer(@PathVariable("id") Long id) {
        OrderEnt order = oRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        Customer customer = cRepository.findById(order.getCustomer().getId()).orElseThrow(() -> new CustomerNotFoundException(order.getCustomer().getId()));

        return cModelAssembler.toModel(customer);
    }

    //get product info from order
    @GetMapping("/order/{id}/product")
    EntityModel<Product> findProduct(@PathVariable("id") Long id) {
        OrderEnt order = oRepository.findById(id).orElseThrow(() -> new OrderNotFoundException(id));
        Product product = pRepository.findById(order.getProduct().getId()).orElseThrow(() -> new CustomerNotFoundException(order.getProduct().getId()));

        return pModelAssembler.toModel(product);
    }

    //create a new order - this create method is unique within this system as it doesn't take an existing order as a request,
    //there is no request needed as it is intended that the system will create the order object to avoid any unnecessary edge-cases
    //to handle later
    @PostMapping("/order/customer/{cId}/product/{pId}/quantity/{quantity}")
    ResponseEntity<?> newOrder(
        @PathVariable("cId") Long cId,
        @PathVariable("pId") Long pId,
        @PathVariable("quantity") int quantity
    ) {
        //create the customer and product entities
        Customer customer = cRepository.findById(cId).orElseThrow(() -> new CustomerNotFoundException(cId));
        Product product = pRepository.findById(pId).orElseThrow(() -> new ProductNotFoundException(pId));
        
        //check if requesting more than available stock
        int stock = product.getStockQuantity();
        if(quantity > stock)
            throw new OrderBadRequestException("Unable to process order. Requested more units than avaible in stock.");
        
        //create order object
        OrderEnt order = new OrderEnt();
        order.setQuantity(quantity);
        order.setCustomer(customer);
        order.setProduct(product);

        //save order object to system and return
        EntityModel<OrderEnt> entityModel = oModelAssembler.toModel(oRepository.save(order));
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    //update existing order - similar to the create new order function, this is unique compared to other update functions within
    //the system. For the same reason as the create method above
    @PutMapping("/order/{oId}/customer/{cId}/product/{pId}/quantity/{quantity}")
    ResponseEntity<?> replaceOrder(
        @PathVariable("oId") Long oId,
        @PathVariable("cId") Long cId,
        @PathVariable("pId") Long pId,
        @PathVariable("quantity") int quantity
    ) {
        //create the customer and product entities
        Customer customer = cRepository.findById(cId).orElseThrow(() -> new CustomerNotFoundException(cId));
        Product product = pRepository.findById(pId).orElseThrow(() -> new ProductNotFoundException(pId));
        
        //check if requesting more than available stock
        int stock = product.getStockQuantity();
        if(quantity > stock)
            throw new OrderBadRequestException("Unable to process order. Requested more units than avaible in stock.");
        
        //create a new order object based on the changes desired 
        OrderEnt newOrder = new OrderEnt();
        newOrder.setQuantity(quantity);
        newOrder.setCustomer(customer);
        newOrder.setProduct(product);
        //attempt to find the order by the order id and replace all variables with that of the new order
        //otherwise just set the new order id to be that of the order id input, and save that new order object instead
        OrderEnt updatedOrder = oRepository.findById(oId).map(order -> {
            order.setQuantity(newOrder.getQuantity());
            order.setCustomer(newOrder.getCustomer());
            order.setProduct(newOrder.getProduct());
            return oRepository.save(order);
        }).orElseGet(() -> {
            newOrder.setId(oId);
            return oRepository.save(newOrder);
        });

        EntityModel<OrderEnt> entityModel = oModelAssembler.toModel(updatedOrder);

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    //delete order entity by id
    @DeleteMapping("/order/{id}")
    ResponseEntity<?> deleteOrder(@PathVariable Long id){
        oRepository.deleteById(id);
        
        return ResponseEntity.noContent().build();
    }
}