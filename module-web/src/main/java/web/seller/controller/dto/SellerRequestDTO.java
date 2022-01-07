package web.seller.controller.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;
import web.seller.domain.Seller;
import web.seller.domain.SellerStatus;
import web.user.domain.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
public class SellerRequestDTO {

    @Email(message = "올바른 이메일 주소가 아닙니다.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "영문, 숫자를 포함한 8자 이상의 비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String passwordConfirm;

    @NotBlank(message = "브랜드명을 입력해주세요.")
    private String brandName;

    @NotBlank(message = "브랜드 설명을 입력해주세요.")
    private String brandContent;

    @NotBlank(message = "등록할 상품에 대한 설명을 입력해주세요.")
    private String itemContent;

    private String imagePath;


    public Seller toEntity() {
        return Seller.builder()
                .email(email)
                .password(password)
                .brandName(brandName)
                .brandContent(brandContent)
                .itemContent(itemContent)
                .imagePath(imagePath)
                .sellerStatus(SellerStatus.WAIT)
                .role(Role.ROLE_SELLER)
                .build();
    }

    public SellerRequestDTO encodePassword(PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(password);
        return this;
    }

    public boolean checkPasswordConfirm() {
        return password.equals(passwordConfirm);
    }
}
