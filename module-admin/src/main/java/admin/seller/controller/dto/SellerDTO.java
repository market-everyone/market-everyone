package admin.seller.controller.dto;

import lombok.*;
import web.seller.domain.SellerStatus;

import java.time.LocalDateTime;

@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SellerDTO {

    private Long id;
    private String email;
    private String password;
    private String brandName;
    private String brandContent;
    private String itemContent;
    private String imagePath;
    private SellerStatus sellerStatus;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
