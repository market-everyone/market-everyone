package web.order.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.item.domain.Item;
import web.item.domain.ItemRepository;
import web.item.domain.category.Category;
import web.order.domain.Order;
import web.order.domain.OrderRepository;
import web.order.domain.item.OrderItem;
import web.user.domain.User;
import web.user.domain.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long order(Long userNo, Long itemNo, int count){

        // Entity 조회
        User user = userRepository.findById(userNo).get();
        Item item = itemRepository.findById(itemNo).get();

        // 주문 상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(user, orderItem);

        // 주문 저장
        orderRepository.save(order);
        return order.getId();
    }

    @Transactional
    public List<Order> findOrders(){
        return orderRepository.findAll();
    }

    @Transactional
    public void cancelOrder(Long orderId){
        Order order = orderRepository.findById(orderId).get();
    }
}
