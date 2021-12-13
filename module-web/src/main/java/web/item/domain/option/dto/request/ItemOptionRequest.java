package web.item.domain.option.dto.request;

import lombok.*;
import web.item.domain.Item;
import web.item.domain.option.ItemOption;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class ItemOptionRequest {

    private String name;
    private String quantity;
    private String price;
    private Item item;

    public ItemOption toItemOption() {
        return ItemOption.builder()
                .name(name)
                .quantity(Integer.parseInt(quantity))
                .item(item)
                .price(Integer.parseInt(price))
                .build();
    }
}
