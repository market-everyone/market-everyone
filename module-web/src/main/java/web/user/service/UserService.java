package web.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.user.controller.dto.request.UserInfoUpdateRequest;
import web.user.controller.dto.request.UserSignUpRequest;
import web.user.controller.dto.response.UserInfoResponse;
import web.user.domain.Address;
import web.user.domain.User;
import web.user.domain.UserRepository;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void save(UserSignUpRequest userSignUpRequest) {
        User user = userSignUpRequest
                .encodePassword(passwordEncoder)
                .toUser();

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserInfoResponse findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        return UserInfoResponse.of(user);
    }

    @Transactional
    public void update(Long id, UserInfoUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        user.update(request);
    }

}
