package OnlineOrdering.Repositories;
import OnlineOrdering.Models.Customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}