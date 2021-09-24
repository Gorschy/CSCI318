package OnlineOrdering.Repositories;
import OnlineOrdering.Models.OrderEnt;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEnt, Long> {

}