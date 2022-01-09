package web.item.domain;
import lombok.*;
import web.common.entity.BaseEntity;
import web.item.controller.dto.request.ItemRequest;
import web.item.domain.category.Category;
import web.item.domain.option.ItemOption;
import web.seller.domain.Seller;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Entity
public class Item{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_no")
    private Category category;

    private String name;
    private int price;
    private int quantity;
    private String content;
    private String exchange;
    private String refund;
    private String delivery;
    private String imageName;
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "seller_no")
    private Seller seller;


    public void update(ItemRequest itemRequest) {

        name = itemRequest.getName();
        price = Integer.parseInt(itemRequest.getPrice());
        quantity = Integer.parseInt(itemRequest.getQuantity());
        content = itemRequest.getContent();
        exchange = itemRequest.getExchange();
        refund = itemRequest.getRefund();
        delivery = itemRequest.getDelivery();
        imageName = itemRequest.getImageName();
        imagePath = itemRequest.getImagePath();
    }
}
