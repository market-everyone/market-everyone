package web.cart.controller.dto.request;

import lombok.*;
import web.cart.domain.Cart;
import web.user.domain.User;

import java.util.HashMap;
import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CartSaveRequest {
    private Long itemId;
    private int itemCount;

    public Cart toCart(User user) {
        return Cart.builder()
                .userId(user.getId())
                .build();
    }
}
