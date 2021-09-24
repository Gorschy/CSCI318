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
class ProductDetailController {

  private final ProductDetailRepository repository;
  private final ProductDetailModelAssembler assembler;

  ProductDetailController(ProductDetailRepository repository, ProductDetailModelAssembler assembler) {
    this.repository = repository;
    this.assembler = assembler;
  }


  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/productDetails")
  CollectionModel<EntityModel<ProductDetail>> all() {

    List<EntityModel<ProductDetail>> productDetail = repository.findAll().stream() //
        .map(assembler::toModel) //
        .collect(Collectors.toList());

    return CollectionModel.of(productDetail, linkTo(methodOn(ProductDetailController.class).all()).withSelfRel());
  }


  // end::get-aggregate-root[]
  @PostMapping("/productDetails")
  ProductDetail newProductDetail(@RequestBody ProductDetail newProductDetail) {
    return repository.save(newProductDetail);
  }

  // Single item
  @GetMapping("/productDetails/{id}")
  EntityModel<ProductDetail> one(@PathVariable Long id) {

    ProductDetail productDetail = repository.findById(id) //
        .orElseThrow(() -> new ProductDetailNotFoundException(id));

    return assembler.toModel(productDetail);
  }



  @PutMapping("/productDetails/{id}")
  ProductDetail replaceProductDetail(@RequestBody ProductDetail newProductDetail, @PathVariable Long id) {
    
    return repository.findById(id)
      .map(productDetail -> {
        productDetail.setDescription(newProductDetail.getDescription());
        productDetail.setComment(newProductDetail.getComment());
        return repository.save(productDetail);
      })
      .orElseGet(() -> {
        newProductDetail.setId(id);
        return repository.save(newProductDetail);
      });
  }

  @DeleteMapping("/productDetails/{id}")
  void deleteProductDetail(@PathVariable Long id) {
    repository.deleteById(id);
  }
}