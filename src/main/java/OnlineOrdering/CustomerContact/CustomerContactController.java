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
class CustomerContactController {

  private final CustomerContactRepository repository;
  private final CustomerContactModelAssembler assembler;

  CustomerContactController(CustomerContactRepository repository, CustomerContactModelAssembler assembler) {
    this.repository = repository;
    this.assembler = assembler;
  }


  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/customerContacts")
  CollectionModel<EntityModel<CustomerContact>> all() {

    List<EntityModel<CustomerContact>> contact = repository.findAll().stream() //
        .map(assembler::toModel) //
        .collect(Collectors.toList());

    return CollectionModel.of(contact, linkTo(methodOn(CustomerContactController.class).all()).withSelfRel());
  }


  // end::get-aggregate-root[]
  @PostMapping("/customerContacts")
  CustomerContact newCustomerContact(@RequestBody CustomerContact newCustomerContact) {
    return repository.save(newCustomerContact);
  }

  // Single item
  @GetMapping("/customerContacts/{id}")
  EntityModel<CustomerContact> one(@PathVariable Long id) {

    CustomerContact contact = repository.findById(id) //
        .orElseThrow(() -> new CustomerContactNotFoundException(id));

    return assembler.toModel(contact);
  }



  @PutMapping("/customerContacts/{id}")
  CustomerContact replaceEmployee(@RequestBody CustomerContact newCustomerContact, @PathVariable Long id) {
    
    return repository.findById(id)
      .map(customerContact -> {
        customerContact.setName(newCustomerContact.getName());
        customerContact.setPhone(newCustomerContact.getPhone());
        customerContact.setEmail(newCustomerContact.getEmail());
        customerContact.setPosition(newCustomerContact.getPosition());
        return repository.save(customerContact);
      })
      .orElseGet(() -> {
        newCustomerContact.setPhone(id);
        return repository.save(newCustomerContact);
      });
  }

  @DeleteMapping("/customerContacts/{id}")
  void deleteEmployee(@PathVariable Long id) {
    repository.deleteById(id);
  }
}