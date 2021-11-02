package Customer;

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
import org.springframework.stereotype.Service;

@Service
public class ContactService {
    @Autowired
    private ContactRepository contRepository;

    // find all contacts in the system and return a list of contacts
    List<Contact> all() {
        return contRepository.findAll();
    }

    // Find a specific contact by id
    Contact one(Long id) {
        return contRepository.findById(id).orElseThrow(() -> new ContactNotFoundException(id));
    }

    // Create a new contact
    Contact newContact(Contact newContact) {
        return contRepository.save(newContact);
    }

    // Update an existing contact
    Contact replaceContact(Long id, Contact newContact) {
        return contRepository.findById(id).map(contact -> {
            contact.setName(newContact.getName());
            contact.setPhone(newContact.getPhone());
            contact.setEmail(newContact.getEmail());
            contact.setPosition(newContact.getPosition());
            return contRepository.save(contact);
        }).orElseGet(() -> {
            newContact.setId(id);
            return contRepository.save(newContact);
        });
    }

    // Delete a contact entity based on id
    void deleteContact(Long id) {
        contRepository.deleteById(id);
    }
}