package web.seller.domain;

import lombok.*;
import web.common.entity.BaseEntity;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Seller extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SELLER_NO")
    private Long id;

    private String email;
    private String password;

    private String brandName;
    private String brandContent;
    private String itemContent;
    private String imagePath;

    @Enumerated(value = EnumType.STRING)
    private SellerStatus sellerStatus;

    @Builder
    public Seller(Long id, String email, String password, String brandName, String brandContent, String itemContent, String imagePath, SellerStatus sellerStatus) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.brandName = brandName;
        this.brandContent = brandContent;
        this.itemContent = itemContent;
        this.imagePath = imagePath;
        this.sellerStatus = sellerStatus;
    }
}
