package com.example.demo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.demo.model.Customer;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class CustomerModelAssembler implements RepresentationModelAssembler<Customer, EntityModel<Customer>>{
    @Override
    public EntityModel<Customer> toModel(Customer customer){
        return EntityModel.of(customer,
            linkTo(methodOn(CustomerController.class).one(customer.getId())).withSelfRel(),
            linkTo(methodOn(CustomerController.class).all()).withRel("customers"));
    }
}