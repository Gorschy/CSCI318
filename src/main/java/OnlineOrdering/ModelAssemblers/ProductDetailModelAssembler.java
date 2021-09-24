package OnlineOrdering.ModelAssemblers;
import OnlineOrdering.Models.ProductDetail;
import OnlineOrdering.Controllers.ProductDetailController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ProductDetailModelAssembler implements RepresentationModelAssembler<ProductDetail, EntityModel<ProductDetail>> {

  @Override
  public EntityModel<ProductDetail> toModel(ProductDetail productDetail) {

    return EntityModel.of(productDetail, //
        linkTo(methodOn(ProductDetailController.class).one(productDetail.getId())).withSelfRel(),
        linkTo(methodOn(ProductDetailController.class).all()).withRel("productDetail"));
  }
}