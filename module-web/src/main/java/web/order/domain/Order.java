package web.order.domain;

import lombok.*;
import web.order.domain.item.OrderItem;
import web.user.domain.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "ORDERS")
@Entity
@Getter @Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_NO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_NO")
    private User user;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime date;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItemList = new ArrayList<>();

    public void addOrderItem(OrderItem orderItem){
        orderItemList.add(orderItem);
        orderItem.setOrder(this);
    }

    public static Order createOrder(User user, OrderItem... orderItems){
        Order order = new Order();

        order.setUser(user);
        order.setStatus(OrderStatus.ORDER);
        order.setDate(LocalDateTime.now());

        for(OrderItem orderItem : orderItems){
            order.addOrderItem(orderItem);
        }

        return order;
    }

    public int getTotalPrice(){
        int totalPrice = 0;
        for(OrderItem orderItem : orderItemList){
            totalPrice += orderItem.getTotalPrice();
        }

        return totalPrice;
    }
}
