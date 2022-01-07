package admin.user.controller.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDTO {

    private String name;

    private String address;
    private String detailAddress;
    private String postcode;

    private String phone;

    private int point;
}
