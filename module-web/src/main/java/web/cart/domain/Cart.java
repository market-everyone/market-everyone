package web.cart.domain;

import lombok.*;
import web.item.domain.Item;

import javax.persistence.*;
import java.util.Map;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CART_NO")
    private Long id;

    private Long userId;

    @ElementCollection
    @CollectionTable(name = "CART_ITEM")
    private Map<Long, CartItem> cartItem;

    public void addCartItem(CartItem item) {
        cartItem.put(item.getItemId(), item);
    }
}
