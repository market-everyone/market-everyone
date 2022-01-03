package web.cart.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
@Embeddable
public class CartItem {
    private Long itemId;
    private int itemCount;
}