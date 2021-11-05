package Product;

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
class ProductController {
    @Autowired
    ProductService service;

    // find all product in system 
    @GetMapping("/products")
    List<Product> all(){
        return service.all();
    }

    // find one product in system by id
    @GetMapping("/products/{id}")
    Product one(@PathVariable Long id){
    	return service.one(id);
    }

    // create a new product
    @PostMapping("/products")
    Product newProduct(@RequestBody Product newProduct){
        return service.newProduct(newProduct);
    }

    // update an existing product
    @PutMapping("/products/{id}")
    Product replaceProduct(@RequestBody Product newProduct, @PathVariable Long id){
    	return service.replaceProduct(id, newProduct);
    }

    // delete a product based on id
    @DeleteMapping("/products/{id}")
    void deleteProduct(@PathVariable Long id){
    	service.deleteProduct(id);
    }
    
    // join a contact object to a customer object
    @PutMapping("/products/{prodId}/productDetails/{prodDetailId}")
    Product joinProductDetail(@PathVariable("prodId") Long prodId, @PathVariable("prodDetailId") Long prodDetailId){
        return service.joinProductDetail(prodId, prodDetailId);
    }
    
}