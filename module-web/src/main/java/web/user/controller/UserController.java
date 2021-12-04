package web.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import web.common.AuthConverter;
import web.user.controller.dto.request.UserSignUpRequest;
import web.user.controller.dto.response.UserInfoResponse;
import web.user.domain.User;
import web.user.service.UserService;

@RequiredArgsConstructor
@RequestMapping("/users")
@Controller
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public String saveUser(@ModelAttribute UserSignUpRequest userSignUpRequest) {
        userService.save(userSignUpRequest);
        return "redirect:login";
    }

    @GetMapping("/mypage")
    public String findUser(Authentication authentication, Model model) {
        User user = AuthConverter.findCurrentUserFromAuth(authentication);
        model.addAttribute("userInfo", UserInfoResponse.of(user));
        return "myInfo";
    }

    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("loginError", "true");
        return "login";
    }

    /* 페이지 */

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/join")
    public String signUpPage() {
        return "join";
    }

    /*      */
}
