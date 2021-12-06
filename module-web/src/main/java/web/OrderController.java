package web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderController {
    @GetMapping("/order/orderList")
    public String OrderList(){
        return "/order/orderList";
    }

    @GetMapping("/order/orderListDetail")
    public String OrderListDetail(){
        return "/order/orderListDetail";
    }

    @GetMapping("/order/ayReady")
    public String PayReady(){
        return "/order/payReady";
    }

    @GetMapping("/order/payComp")
    public String PayComp(){
        return "/order/payComp";
    }
}
