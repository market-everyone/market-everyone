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

    private String name;

    private String phone;

    private String postcode;
    private String address;
    private String detailAddress;

    private String memo;

    public static UserInfoResponse of(User user) {
        UserInfoResponse userInfoResponse = UserInfoResponse.builder()
                .accountId(user.getAccountId())
                .email(user.getEmail())
                .point(user.getPoint())
                .name(user.getName())
                .phone(user.getPhone())
                .memo(user.getMemo())
                .build();

        if (user.getAddress() != null) {
            userInfoResponse.postcode = user.getAddress().getPostcode();
            userInfoResponse.address = user.getAddress().getAddress();
            userInfoResponse.detailAddress = user.getAddress().getDetailAddress();
        }

        return userInfoResponse;
    }
}
