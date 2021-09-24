package OnlineOrdering.ModelAssemblers;
import OnlineOrdering.Models.OrderEnt;
import OnlineOrdering.Controllers.OrderController;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class OrderModelAssembler implements RepresentationModelAssembler<OrderEnt, EntityModel<OrderEnt>>{
    @Override
    public EntityModel<OrderEnt> toModel(OrderEnt order){
        return EntityModel.of(order,
            linkTo(methodOn(OrderController.class).one(order.getId())).withSelfRel(),
            linkTo(methodOn(OrderController.class).all()).withRel("orders"));
    }
}