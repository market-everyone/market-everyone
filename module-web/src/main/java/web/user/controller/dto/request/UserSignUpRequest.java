package web.user.controller.dto.request;

import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import web.user.domain.Address;
import web.user.domain.Role;
import web.user.domain.User;

@Builder
@AllArgsConstructor
@Setter
@Getter
public class UserSignUpRequest {
    private static final int INITIAL_POINT_ZERO = 0;

    private String accountId;

    private String email;

    private String password;

    private String confirmPassword;

    private String city;

    private String street;

    public User toUser() {
        return User.builder()
                .accountId(accountId)
                .email(email)
                .password(password)
                .address(new Address(city, street))
                .role(Role.ROLE_USER)
                .point(INITIAL_POINT_ZERO)
                .build();
    }

    public UserSignUpRequest encodePassword(PasswordEncoder passwordEncoder) {
        password = passwordEncoder.encode(password);
        return this;
    }
}
