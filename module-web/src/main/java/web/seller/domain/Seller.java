package web.seller.domain;

import lombok.*;
import web.common.entity.BaseEntity;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
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
}
