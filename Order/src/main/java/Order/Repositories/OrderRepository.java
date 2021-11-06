package OrderEntity;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    List<OrderEntity> findByCustId(Long custId);
    List<OrderEntity> findByProdId(Long prodId);
}