package admin.seller.controller.dto;

import lombok.Getter;
import web.seller.domain.Seller;
import web.seller.domain.SellerStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Getter
public class SellerResponseDTO {

    private Long id;
    private String email;
    private String password;
    private String brandName;
    private String brandContent;
    private String itemContent;
    private String imagePath;
    private SellerStatus sellerStatus;
    private String createDate;
    private String modifiedDate;

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
