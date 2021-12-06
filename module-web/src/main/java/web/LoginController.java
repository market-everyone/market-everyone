package web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/user/login")
    public String Login(){
        return "/user/login";
    }

    @GetMapping("/user/join")
    public String Join(){
        return "/user/join";
    }
}