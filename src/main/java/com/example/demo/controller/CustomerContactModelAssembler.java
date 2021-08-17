package com.example.demo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.demo.model.CustomerContact;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
class CustomerContactModelAssembler implements RepresentationModelAssembler<CustomerContact, EntityModel<CustomerContact>> {

  @Override
  public EntityModel<CustomerContact> toModel(CustomerContact contact) {

    return EntityModel.of(contact, //
        linkTo(methodOn(CustomerContactController.class).one(contact.getId())).withSelfRel(),
        linkTo(methodOn(CustomerContactController.class).all()).withRel("contacts"));
  }
}