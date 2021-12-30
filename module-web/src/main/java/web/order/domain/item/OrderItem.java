package web.order.domain.item;

import lombok.*;
import web.item.domain.Item;
import web.order.domain.Order;

import javax.persistence.*;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ORDER_ITEM_NO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_NO")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_NO")
    private Order order;

    private int count;
    private int price;

    public static OrderItem createOrderItem(Item item, int price, int count){
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setPrice(price);
        orderItem.setCount(count);

//        item.removeStock(count); // 재고 감소 (예정)
        return orderItem;
    }

//
//    public void cancel(){
//        getItem().add
//    }

    public int getTotalPrice(){
        return getPrice() * getCount();
    }
}