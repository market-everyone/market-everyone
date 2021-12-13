package web.user.domain;

import lombok.*;
import web.user.controller.dto.request.UserInfoUpdateRequest;

import javax.persistence.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String nickname;

    private String password;

    private String name;

    private String phone;

    private String memo;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private Role role;

    private int point;

    private String provider;
    private String providerId;

    public void update(UserInfoUpdateRequest request) {
        if (!request.getNickname().equals("")) {
            nickname = request.getNickname();
        }
        if (!request.getAddress().equals("")) {
            address = new Address(
                    request.getPostcode(),
                    request.getAddress(),
                    request.getDetailAddress());
        }
        if (!request.getName().equals("")) {
            name = request.getName();
        }
        if (!request.getPhone().equals("")) {
            phone = request.getPhone();
        }
        if (!request.getMemo().equals("")) {
            memo = request.getMemo();
        }
    }
}
