package web.unit.order;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.annotation.Rollback;
import web.item.domain.Item;
import web.item.domain.ItemRepository;
import web.order.domain.Order;
import web.order.domain.OrderRepository;
import web.order.domain.OrderStatus;
import web.order.domain.item.OrderItem;
import web.order.service.OrderService;
import web.user.domain.User;
import web.user.domain.UserRepository;

import static org.mockito.BDDMockito.given;

public class OrderServiceTest {

    @InjectMocks
    OrderService orderService;
    @Mock
    OrderRepository orderRepository;
    @Mock
    ItemRepository itemRepository;
    @Mock
    UserRepository userRepository;

    @Test
    @Rollback(value = false)
    public void 상품_주문하기() throws Exception{

        // given
        User user = createUser(1L);
        Item item = createItem(1L,20000, 10);
        int orderCount = 2; // item 주문 갯수

        Order order = Order.builder()
                .id(1L)
//                .orderItems(Arrays.asList(orderItem))
                .user(user)
                .status(OrderStatus.ORDER)
                .build();

        OrderItem orderItem = OrderItem.builder()
                .id(1L)
                .item(item)
                .order(order)
                .build();

        order.addOrderItem(orderItem);

        given(itemRepository.findById(item.getId())).willReturn(java.util.Optional.of(item));
        given(userRepository.findById(user.getId())).willReturn(java.util.Optional.of(user));
//        given(orderRepository.save(any())).willReturn(order);

        // when
        Long orderId = orderService.order(user.getId(), item.getId(), orderCount);
        orderService.order(user.getId(), item.getId(), orderCount);
    }

    @Test
    public void 주문_취소하기(){

    }

    //user factory class
    private User createUser(Long _id){
        return User.builder()
                .id(_id)
                .build();
//        User user = new User(1L, "abc.d", "nick", "pass", "na", "phon", "me", new Address("123-456", "인천", "남동구"), Role.ROLE_USER, 10, "23", "12");
//        User newUser = userRepository.save(user);
//        return newUser;
    }

    private Item createItem(Long _id, int _price, int _quantity){
        return Item.builder()
                .id(_id)
                .price(_price)
                .quantity(_quantity)
                .build();
    }

    private Order createOrder(Long _id, User _user){
        return Order.builder()
                .id(_id)
                .user(_user)
                .build();
    }

    private OrderItem orderItem(Long _id, Item _item){
        return OrderItem.builder()
                .id(_id)
                .item(_item)
                .build();
    }
}
