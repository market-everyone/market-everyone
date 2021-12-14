package web.item.controller.dto.response;

import lombok.*;
import web.item.domain.Item;

@Builder
@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class ItemResponse {
    private Long id;
    private String name;
    private String imagePath;
    private int price;

    public static ItemResponse of(Item item) {
        return ItemResponse.builder()
                .id(item.getId())
                .name(item.getName())
                .imagePath(item.getImagePath())
                .price(item.getPrice())
                .build();
    }
}
