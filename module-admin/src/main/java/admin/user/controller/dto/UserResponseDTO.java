package admin.user.controller.dto;

import lombok.Getter;
import web.user.domain.Address;
import web.user.domain.User;

import java.time.LocalDateTime;

@Getter
public class UserResponseDTO {

    private final Long id;

    private final String email;

    private final String nickname;

    private final String name;

    private final String phone;

    private final String memo;

    private final Address address;

    private final int point;

    private final String provider;

    private final String providerId;

    private final LocalDateTime createDate;

    private final LocalDateTime modifiedDated;

    public UserResponseDTO(User entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.nickname = entity.getNickname();
        this.name = entity.getName();
        this.phone = entity.getPhone();
        this.memo = entity.getMemo();
        this.address = entity.getAddress();
        this.point = entity.getPoint();
        this.provider = entity.getProvider();
        this.providerId = entity.getProviderId();
        this.createDate = entity.getCreateDate();
        this.modifiedDated = entity.getModifiedDate();
    }
}
