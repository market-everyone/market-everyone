package admin.seller.controller.dto;

import lombok.Getter;
import web.seller.domain.Seller;
import web.seller.domain.SellerStatus;

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

    private String toStringDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Optional.ofNullable(localDateTime)
                .map(formatter::format)
                .orElse("");
    }
}
