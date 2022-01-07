package web.seller.controller.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class SellerLoginDTO {

    @Email(message = "올바른 이메일 주소가 아닙니다.")
    @NotBlank
    private String email;

    @NotBlank
    private String password;
}
