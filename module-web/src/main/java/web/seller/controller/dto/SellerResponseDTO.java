package web.seller.controller.dto;

import lombok.Builder;
import web.seller.domain.Seller;

public class SellerResponseDTO {

    private String email;
    private String brandName;

    @Builder
    public Seller toSeller() {
        return Seller.builder()
                .email(email)
                .brandName(brandName)
                .build();
    }
}
