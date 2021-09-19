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
class ContactController {

  private final ContactRepository repository;
  private final ContactModelAssembler assembler;

  ContactController(ContactRepository repository, ContactModelAssembler assembler) {
    this.repository = repository;
    this.assembler = assembler;
  }


  // Aggregate root
  // tag::get-aggregate-root[]
  @GetMapping("/contacts")
  CollectionModel<EntityModel<Contact>> all() {

    List<EntityModel<Contact>> contact = repository.findAll().stream() //
        .map(assembler::toModel) //
        .collect(Collectors.toList());

    return CollectionModel.of(contact, linkTo(methodOn(ContactController.class).all()).withSelfRel());
  }


  // end::get-aggregate-root[]
  @PostMapping("/contacts")
  Contact newContact(@RequestBody Contact newContact) {
    return repository.save(newContact);
  }

  // Single item
  @GetMapping("/contacts/{id}")
  EntityModel<Contact> one(@PathVariable Long id) {

    Contact contact = repository.findById(id) //
        .orElseThrow(() -> new ContactNotFoundException(id));

    return assembler.toModel(contact);
  }



  @PutMapping("/contacts/{id}")
  Contact replaceEmployee(@RequestBody Contact newContact, @PathVariable Long id) {
    
    return repository.findById(id)
      .map(contact -> {
        contact.setName(newContact.getName());
        contact.setPhone(newContact.getPhone());
        contact.setEmail(newContact.getEmail());
        contact.setPosition(newContact.getPosition());
        return repository.save(contact);
      })
      .orElseGet(() -> {
        newContact.setId(id);
        return repository.save(newContact);
      });
  }

  @DeleteMapping("/contacts/{id}")
  void deleteEmployee(@PathVariable Long id) {
    repository.deleteById(id);
  }
}