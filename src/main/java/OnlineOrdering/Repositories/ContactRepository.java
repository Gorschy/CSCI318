package OnlineOrdering.Repositories;
import OnlineOrdering.Models.Contact;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {

}