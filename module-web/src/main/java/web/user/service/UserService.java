package web.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import web.user.controller.dto.request.UserSignUpRequest;
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
}
