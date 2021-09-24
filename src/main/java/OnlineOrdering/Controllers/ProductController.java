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
public class ProductController {
    private final ProductRepository prodRepository;
    private final ProductDetailRepository prodDetailRepository;
    private final ProductModelAssembler prodAssembler;
    private final ProductDetailModelAssembler prodDetailAssembler;


    ProductController(ProductRepository prodRepository, ProductDetailRepository prodDetailRepository, ProductModelAssembler prodAssembler, ProductDetailModelAssembler prodDetailAssembler) {
        this.prodRepository = prodRepository;
        this.prodDetailRepository = prodDetailRepository;
        this.prodAssembler = prodAssembler;
        this.prodDetailAssembler = prodDetailAssembler;
    }

    // find all product in system 
    @GetMapping("/products")
    public CollectionModel<EntityModel<Product>> all(){
        List<EntityModel<Product>> product = prodRepository.findAll().stream().map(prodAssembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(product,
            linkTo(methodOn(ProductController.class).all()).withSelfRel());
    }

    // find one product in system by id
    @GetMapping("/products/{id}")
    public EntityModel<Product> one(@PathVariable Long id){
    	Product product = prodRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        return prodAssembler.toModel(product);
    }

    // create a new product without productDetail
    @PostMapping("/product/{category}/{name}/{price}/{quantity}")
    ResponseEntity<?> newProduct(
        @PathVariable String category,
        @PathVariable String name,
        @PathVariable double price,
        @PathVariable int quantity
    ) {
        Product newProduct = new Product(category, name, price, quantity);

        EntityModel<Product> entityModel = prodAssembler.toModel(prodRepository.save(newProduct));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
    // create a new product with productDetail
    @PostMapping("/product/{category}/{name}/{price}/{quantity}/{detId}")
    ResponseEntity<?> newProduct(
        @PathVariable String category,
        @PathVariable String name,
        @PathVariable double price,
        @PathVariable int quantity,
        @PathVariable Long detId
    ) {
        Product newProduct = new Product(category, name, price, quantity);

        ProductDetail productDetail = prodDetailRepository.findById(detId).orElseThrow(() -> new ProductDetailNotFoundException(detId));
        newProduct.setProductDetail(productDetail);

        EntityModel<Product> entityModel = prodAssembler.toModel(prodRepository.save(newProduct));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
    
    // update an existing product without productDetail
    @PutMapping("/product/{prodId}/{category}/{name}/{price}/{quantity}")
    ResponseEntity<?> newProduct(
        @PathVariable Long prodId,
        @PathVariable String category,
        @PathVariable String name,
        @PathVariable double price,
        @PathVariable int quantity
    ) {
        Product newProduct = new Product(category, name, price, quantity);

        Product updatedProduct = prodRepository.findById(prodId).map(product -> {
            product.setProductCategory(newProduct.getProductCategory());
            product.setName(newProduct.getName());
            product.setPrice(newProduct.getPrice());
            product.setStockQuantity(newProduct.getStockQuantity());
            product.setProductDetail(newProduct.getProductDetail());
            return prodRepository.save(product);
        }).orElseGet(() -> {
            newProduct.setId(prodId);
            return prodRepository.save(newProduct);
        });

        EntityModel<Product> entityModel = prodAssembler.toModel(updatedProduct);
    
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
    // update an existing product with productDetail
    @PutMapping("/product/{prodId}/{category}/{name}/{price}/{quantity}/{detId}")
    ResponseEntity<?> newProduct(
        @PathVariable Long prodId,
        @PathVariable String category,
        @PathVariable String name,
        @PathVariable double price,
        @PathVariable int quantity,
        @PathVariable Long detId
    ) {
        Product newProduct = new Product(category, name, price, quantity);

        ProductDetail productDetail = prodDetailRepository.findById(detId).orElseThrow(() -> new ProductDetailNotFoundException(detId));
        newProduct.setProductDetail(productDetail);

        Product updatedProduct = prodRepository.findById(prodId).map(product -> {
            product.setProductCategory(newProduct.getProductCategory());
            product.setName(newProduct.getName());
            product.setPrice(newProduct.getPrice());
            product.setStockQuantity(newProduct.getStockQuantity());
            product.setProductDetail(newProduct.getProductDetail());
            return prodRepository.save(product);
        }).orElseGet(() -> {
            newProduct.setId(prodId);
            return prodRepository.save(newProduct);
        });

        EntityModel<Product> entityModel = prodAssembler.toModel(updatedProduct);
    
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    // delete a product based on id
    @DeleteMapping("/products/{id}")
    ResponseEntity<?> deleteProduct(@PathVariable Long id){
    	prodRepository.deleteById(id);
        
        return ResponseEntity.noContent().build();
    }
    // Error with findById being accessed from a non-static context.
    // join a contact object to a customer object
    @PutMapping("/products/{prodId}/productDetails/{prodDetailId}")
    ResponseEntity<?> joinProductDetailProduct(@PathVariable("prodId") Long prodId, @PathVariable("prodDetailId") Long prodDetailId){
        Product product = prodRepository.findById(prodId).orElseThrow(() -> new ProductNotFoundException(prodId));
        ProductDetail productDetail = prodDetailRepository.findById(prodDetailId).orElseThrow(() -> new ProductDetailNotFoundException(prodDetailId));

        product.setProductDetail(productDetail);
        prodRepository.save(product);
        EntityModel<Product> entityModel = prodAssembler.toModel(product);
        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }
    
}