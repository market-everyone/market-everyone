package web.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.common.auth.AuthConverter;
import web.user.controller.dto.request.UserInfoUpdateRequest;
import web.user.controller.dto.request.UserSignUpRequest;
import web.user.controller.dto.response.UserInfoResponse;
import web.user.domain.User;
import web.user.service.UserService;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping("/users")
@Controller
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public String saveUser(@Valid @ModelAttribute(name = "userSignUpRequest") UserSignUpRequest userSignUpRequest,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            userService.checkUserSignUpRequestValidation(userSignUpRequest, model);
            return "user/join";
        }

        if (!userService.checkUserSignUpRequestValidation(userSignUpRequest, model)) {
            return "user/join";
        }

        userService.save(userSignUpRequest);
        return "redirect:login";
    }

    @GetMapping("/mypage")
    public String findUser(Authentication authentication, Model model) {
        User user = AuthConverter.findCurrentUserFromAuth(authentication);
        UserInfoResponse userInfoResponse = userService.findById(user.getId());
        model.addAttribute("userInfo", userInfoResponse);
        return "user/myInfo";
    }

    @PostMapping("/update")
    public String updateUser(@ModelAttribute UserInfoUpdateRequest userInfoUpdateRequest,
                                Authentication authentication) {
        User user =AuthConverter.findCurrentUserFromAuth(authentication);
        userService.update(user.getId(), userInfoUpdateRequest);
        return "redirect:/users/mypage";
    }

    @GetMapping("/login/error")
    public String loginError(Model model) {
        model.addAttribute("error", "true");
        return "user/login";
    }

    /* 페이지 */

    @GetMapping("/login")
    public String loginPage() {
        return "user/login";
    }

    @GetMapping("/join")
    public String signUpPage(@ModelAttribute UserSignUpRequest userSignUpRequest) {
        return "user/join";
    }

    /*      */
}
