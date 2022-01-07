package admin.seller.controller.dto;

import lombok.Getter;
import web.seller.domain.Seller;
import web.seller.domain.SellerStatus;

import static web.util.CustomStringUtils.toStringDateTime;

@Getter
public class SellerResponseDTO {

    private final Long id;
    private final String email;
    private final String password;
    private final String brandName;
    private final String brandContent;
    private final String itemContent;
    private final String imagePath;
    private final SellerStatus sellerStatus;
    private final String createDate;
    private final String modifiedDate;

    public SellerResponseDTO(Seller entity) {
        id = entity.getId();
        email = entity.getEmail();
        password = entity.getPassword();
        brandName = entity.getBrandName();
        brandContent = entity.getBrandContent();
        itemContent = entity.getItemContent();
        imagePath = entity.getImagePath();
        sellerStatus = entity.getSellerStatus();
        createDate = toStringDateTime(entity.getCreateDate());
        modifiedDate = toStringDateTime(entity.getModifiedDate());
    }
}
