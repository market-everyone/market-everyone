package web.order.domain.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    long countByItemId(Long id);

    @Query(value = " select coalesce(sum (m.price), 0)" +
                   " from OrderItem m" +
                   " where m.item.id= :id")
    long findByItemId(@Param("id") Long id);
}
