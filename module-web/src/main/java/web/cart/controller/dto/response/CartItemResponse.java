package web.cart.controller.dto.response;

import lombok.*;
import web.item.domain.Item;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class CartItemResponse {

    private String itemName;
    private int stockQuantity;
    private int itemPrice;
    private int itemCount;

    public static CartItemResponse of(Item item, int itemCount) {
        return CartItemResponse.builder()
                .itemName(item.getName())
                .stockQuantity(item.getQuantity())
                .itemPrice(item.getPrice())
                .itemCount(itemCount)
                .build();
    }
}
