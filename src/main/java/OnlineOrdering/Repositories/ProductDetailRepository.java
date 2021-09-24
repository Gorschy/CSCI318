package OnlineOrdering.Repositories;
import OnlineOrdering.Models.ProductDetail;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {

}