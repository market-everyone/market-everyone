package web.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.item.domain.Item;
import web.item.service.ItemService;
import web.order.domain.Order;
import web.order.service.OrderService;
import web.user.domain.User;
import web.user.service.UserService;

import java.util.List;

@Controller
@RequiredArgsConstructor
//@RequestMapping("order")
public class OrderController {

    private final OrderService orderService;
    private final UserService userService;
    private final ItemService itemService;

    @GetMapping(value = "/order")
    public String createForm(Model model){
//        List<User> users = userService.findUsers();
        List<Item> items = itemService.itemList();

//        model.addAttribute("users", users);
        model.addAttribute("items", items);

        return "order/tmp_orderForm";
    }

    @PostMapping(value = "/order")
    public String order(@RequestParam("user_no") Long userId,
                        @RequestParam("item_no") Long itemId,
                        @RequestParam("count") int count){

        orderService.order(userId, itemId, count);

        return "redirect:/orders";
    }

//    public String orderList(Model model){
//        List<Order> orders = orderService.findOrders(order)
//    }

    /////
    @GetMapping("/order/orderList")
    public String OrderList(Model model){
        List<Order> orders = orderService.findOrders();
        model.addAttribute("orders", orders);
        return "order/orderList";
    }

    @GetMapping("/order/orderListDetail")
    public String OrderListDetail(){
        return "/order/orderListDetail";
    }

    @GetMapping("/order/payReady")
    public String PayReady(){
        return "/order/payReady";
    }

    @GetMapping("/order/payComp")
    public String PayComp(){
        return "/order/payComp";
    }

}
