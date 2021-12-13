package web.user.controller.dto.request;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.security.crypto.password.PasswordEncoder;
import web.user.domain.Address;
import web.user.domain.Role;
import web.user.domain.User;

import javax.validation.constraints.*;

@Builder
@AllArgsConstructor
@Setter
@Getter
public class UserSignUpRequest {
    private static final int INITIAL_POINT_ZERO = 0;

    @Email(message = "올바른 이메일 주소가 아닙니다.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$",
            message = "영문, 숫자를 포함한 8자 이상의 비밀번호를 입력해주세요.")
    private String password;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    private String passwordConfirm;

    @NotBlank(message = "닉네임을 입력해주세요.")
    private String nickname;

    public User toUser() {
        return User.builder()
                .nickname(nickname)
                .email(email)
                .password(password)
                .role(Role.ROLE_USER)
                .point(INITIAL_POINT_ZERO)
                .build();
    }

    public UserSignUpRequest encodePassword(PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(password);
        return this;
    }

    public boolean checkPasswordConfirm() {
        return password.equals(passwordConfirm);
    }

}
