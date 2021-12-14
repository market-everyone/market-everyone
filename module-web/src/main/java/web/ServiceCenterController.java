package web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/service")
@Controller
public class ServiceCenterController {

    @GetMapping("/notice")
    public String notice() {
        return "service/notice";
    }

    @GetMapping("/faq")
    public String faq() {
        return "service/faq";
    }

    @GetMapping("/qna")
    public String qna() {
        return "service/personal-qna-form";
    }

    @GetMapping("/myqna")
    public String myQna() {
        return "service/my-qna";
    }

    @GetMapping("/seller/register")
    public String sellerRegister() {
        return "service/seller-register-form";
    }
}
