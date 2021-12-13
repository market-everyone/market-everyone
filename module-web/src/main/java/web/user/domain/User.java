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
        nickname = request.getNickname();
        address = new Address(
                request.getPostcode(),
                request.getAddress(),
                request.getDetailAddress());
        name = request.getName();
        phone = request.getPhone();
        memo = request.getMemo();
    }
}
