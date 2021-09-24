package OnlineOrdering.Repositories;
import OnlineOrdering.Models.Product;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
