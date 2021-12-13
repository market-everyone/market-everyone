package web.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import web.exception.user.*;
import web.user.controller.dto.request.UserInfoUpdateRequest;
import web.user.controller.dto.request.UserSignUpRequest;
import web.user.controller.dto.response.UserInfoResponse;
import web.user.domain.Address;
import web.user.domain.User;
import web.user.domain.UserRepository;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void save(UserSignUpRequest userSignUpRequest) {
        User user = userSignUpRequest
                .encodePassword(passwordEncoder)
                .toUser();

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public UserInfoResponse findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);

        return UserInfoResponse.of(user);
    }

    @Transactional
    public void update(Long id, UserInfoUpdateRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(IllegalArgumentException::new);

        user.update(request);
    }

    @Transactional(readOnly = true)
    public boolean checkUserSignUpRequestValidation(UserSignUpRequest userSignUpRequest, Model model) {
        Map<String, String> errors = new HashMap<>();
        if (userRepository.existsByEmail(userSignUpRequest.getEmail())) {
            errors.put("emailError", "true");
        }

        if (userRepository.existsByNickname(userSignUpRequest.getNickname())) {
            errors.put("nicknameError", "true");
        }

        if (!userSignUpRequest.checkPasswordConfirm()) {
            errors.put("passwordError", "true");
        }

        model.addAttribute("errors", errors);
        return errors.size() == 0;
    }
}
