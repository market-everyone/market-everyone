package admin.user.controller;

import admin.common.dto.PageRequestDTO;
import admin.user.controller.dto.UserRequestDTO;
import admin.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public String userList(PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("result", userService.findPageUser(pageRequestDTO));
        return "user/user_list";
    }

    @GetMapping("/{userNo}")
    public String userPage(@PathVariable Long userNo, Model model) {
        model.addAttribute("user", userService.findUser(userNo));
        return "user/user";
    }

    @GetMapping("/{userNo}/edit")
    public String updateForm(@PathVariable Long userNo, Model model) {
        model.addAttribute("user", userService.findUser(userNo));
        return "user/edit_form";
    }

    @PostMapping("/{userNo}/edit")
    public String update(@PathVariable Long userNo,
                         UserRequestDTO requestDTO,
                         RedirectAttributes redirectAttributes) {
        userService.update(userNo, requestDTO);
        redirectAttributes.addAttribute("status", true);
        return "redirect:/user/{userNo}";
    }

    @PostMapping("/{userNo}/delete")
    public String delete(@PathVariable Long userNo) {
        userService.remove(userNo);
        return "redirect:/user";
    }
}
