package web.order.domain.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    long countByItemId(Long id);

    @Query(value = " select sum (m.price)" +
                   " from OrderItem m" +
                   " where m.item.id= :id")
    long findByItemId(@Param("id") Long id);
}
