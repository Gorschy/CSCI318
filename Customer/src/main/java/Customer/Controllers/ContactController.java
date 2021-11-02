package Customer;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
class ContactController {

  @Autowired
  ContactService service;


  // find all contacts in system 
  @GetMapping("/contacts")
  List<Contact> all(){
      return service.all();
  }

  // find one contact in system by id
  @GetMapping("/contacts/{id}")
  Contact one(@PathVariable Long id){
      return service.one(id);
  }

  // create a new contact
  @PostMapping("/contacts")
  Contact newContact(@RequestBody Contact newContact){
      return service.newContact(newContact);
  }

  // update an existing contact
  @PutMapping("/contacts/{id}")
  Contact replaceContact(@RequestBody Contact newContact, @PathVariable Long id){
      return service.replaceContact(id, newContact);
  }

  // delete a contact based on id
  @DeleteMapping("/contacts/{id}")
  void deleteContact(@PathVariable Long id){
      service.deleteContact(id);
  }
}