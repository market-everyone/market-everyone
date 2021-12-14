package web.user.controller.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserInfoUpdateRequest {
    private String nickname;

    private String name;
    private String phone;

    private String postcode;
    private String address;
    private String detailAddress;
    private String memo;
}
