package web.unit;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import web.item.domain.Item;
import web.item.domain.category.Category;
import web.order.domain.Order;
import web.order.domain.OrderRepository;
import web.order.domain.OrderStatus;
import web.order.domain.item.OrderItem;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
public class OrderRepoTest {

    @Autowired
    OrderRepository orderRepository;

    @Test
    public void testOrder(){
        OrderItem orderItem = OrderItem.createOrderItem(
                Item.builder()
                        .id(1L)
//                        .category(new Category(2L, "3", "4"))
                        .price(10000)
                        .quantity(2)
                        .build()
                , 10000, 5);


//        Order order = new Order(1, 1, OrderStatus.ORDER, LocalDateTime.now(), (1,2));
//        order.setOrderItems();

    }
}
