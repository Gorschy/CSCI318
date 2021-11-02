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
class ProductDetailController {
  @Autowired
  ProductDetailService service;


  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/productDetails")
  List<ProductDetail> all() {
    return service.all();
  }


  // end::get-aggregate-root[]
  @PostMapping("/productDetails")
  ProductDetail newProductDetail(@RequestBody ProductDetail newProductDetail) {
    return service.newProductDetail(newProductDetail);
  }

  // Single item
  @GetMapping("/productDetails/{id}")
  ProductDetail one(@PathVariable Long id) {
    return service.one(id);
  }

  @PutMapping("/productDetails/{id}")
  ProductDetail replaceProductDetail(@RequestBody ProductDetail newProductDetail, @PathVariable Long id) { 
    return service.replaceProductDetail(id, newProductDetail);
  }

  @DeleteMapping("/productDetails/{id}")
  void deleteProductDetail(@PathVariable Long id) {
    service.deleteProductDetail(id);
  }
}