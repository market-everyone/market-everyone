package web.item.controller.dto.request;


import lombok.*;
import web.item.domain.Item;
import web.item.domain.category.Category;

@Builder
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class ItemRequest {

    private String name;
    private String price;
    private String quantity;
    private String content;
    private String exchange;
    private String refund;
    private String delivery;
    private String imageName;
    private String imagePath;
    private Category category;


    public Item toItem() {
        return Item.builder()
                .name(name)
                .price(Integer.parseInt(price))
                .quantity(Integer.parseInt(quantity))
                .content(content)
                .exchange(exchange)
                .refund(refund)
                .imageName(imageName)
                .imagePath(imagePath)
                .category(category)
                .delivery(delivery)
                .build();
    }


}
