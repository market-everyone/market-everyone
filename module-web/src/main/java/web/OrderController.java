package web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {
    @GetMapping("/orderList")
    public String OrderList(){
        return "orderList";
    }

    @GetMapping("/orderListDetail")
    public String OrderListDetail(){
        return "orderListDetail";
    }

    @GetMapping("payReady")
    public String PayReady(){
        return "payReady";
    }

    @GetMapping("payComp")
    public String PayComp(){
        return "payComp";
    }
}
