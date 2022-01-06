package web.user.domain;

import lombok.*;
import web.common.entity.BaseEntity;
import web.user.controller.dto.request.UserInfoUpdateRequest;

import javax.persistence.*;

@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@Entity
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_NO")
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

    public void update(String name,
                       String address,
                       String detailAddress,
                       String postcode,
                       String phone,
                       int point) {
        if (!name.isBlank()) {
            this.name = name;
        }
        if (!address.isBlank()
                && !detailAddress.isBlank()
                && !postcode.isBlank()) {
            this.address = new Address(postcode, address, detailAddress);
        }
        if (!phone.isBlank()) {
            this.phone = phone;
        }
        this.point = point;
    }
}
