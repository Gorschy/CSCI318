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
class ProductController {
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
    CollectionModel<EntityModel<Product>> all(){
        List<EntityModel<Product>> product = prodRepository.findAll().stream().map(prodAssembler::toModel).collect(Collectors.toList());

        return CollectionModel.of(product,
            linkTo(methodOn(ProductController.class).all()).withSelfRel());
    }

    // find one product in system by id
    @GetMapping("/products/{id}")
    EntityModel<Product> one(@PathVariable Long id){
    	Product product = prodRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        return prodAssembler.toModel(product);
    }

    // create a new product
    @PostMapping("/products")
    ResponseEntity<?> newProduct(@RequestBody Product newProduct){
        EntityModel<Product> entityModel = prodAssembler.toModel(prodRepository.save(newProduct));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
    }

    // update an existing product
    @PutMapping("/products/{id}")
    ResponseEntity<?> replaceProduct(@RequestBody Product newProduct, @PathVariable Long id){
    	Product updatedProduct = prodRepository.findById(id).map(product -> {
    		product.setProductCategory(newProduct.getProductCategory());
    		product.setName(newProduct.getName());
    		product.setPrice(newProduct.getPrice());
    		product.setStockQuantity(newProduct.getStockQuantity());
            return prodRepository.save(product);
        }).orElseGet(() -> {
            newProduct.setId(id);
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