package admin.seller.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import web.seller.domain.Seller;
import web.seller.domain.SellerStatus;

@Getter
@Setter
public class SellerRequestDTO {

    private String brandName;
    private String brandContent;
    private String itemContent;
    private String imagePath;
    private SellerStatus sellerStatus;

    @Builder
    public SellerRequestDTO(String brandName, String brandContent, String itemContent, String imagePath, SellerStatus sellerStatus) {
        this.brandName = brandName;
        this.brandContent = brandContent;
        this.itemContent = itemContent;
        this.imagePath = imagePath;
        this.sellerStatus = sellerStatus;
    }

    public Seller toEntity() {
        return Seller.builder()
                .brandName(brandName)
                .brandContent(brandContent)
                .itemContent(itemContent)
                .imagePath(imagePath)
                .sellerStatus(sellerStatus)
                .build();
    }
}
