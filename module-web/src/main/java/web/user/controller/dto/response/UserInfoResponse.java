package web.user.controller.dto.response;

import lombok.*;
import web.user.domain.User;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserInfoResponse {
    private String accountId;

    private String email;

    private int point;

    public static UserInfoResponse of(User user) {
        return UserInfoResponse.builder()
                .accountId(user.getAccountId())
                .email(user.getEmail())
                .point(user.getPoint())
                .build();
    }
}
