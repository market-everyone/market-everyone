package web.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import web.item.domain.Item;
import web.item.service.ItemService;
import web.order.domain.Order;
import web.order.service.OrderService;
import web.security.UserPrincipal;
import web.user.domain.User;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("order")
public class OrderController {

    private final OrderService orderService;
    private final ItemService itemService;

    @PostMapping(value = "/order")
    public String order(@RequestParam("user_no") Long userId,
                        @RequestParam("item_no") Long itemId,
                        @RequestParam("count") int count){

        orderService.order(userId, itemId, count);
        return "redirect:/orders";
    }

    @GetMapping("/orderList")
    public String OrderList(
            //@AuthenticationPrincipal UserPrincipal userPrincipal,
                            Model model){

        List<Order> orders = orderService.findOrders();
        model.addAttribute("orders", orders);
        return "order/orderList";
    }

    // 주문 폼 이동
    @PostMapping("/orderForm")
    public String createOrderForm(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                  @RequestParam("itemId") Long itemNo,
                                  @RequestParam("quantity") int count,
                                  Model model){

        Item item = itemService.findById(itemNo);
        User user = userPrincipal.getUser();

        model.addAttribute("item", item);
        model.addAttribute("user", user);
        model.addAttribute("count", count);
        return "order/orderForm";
    }

    // 주문 완료
    @PostMapping("/orderCompleted")
    public String orderCompleted(@RequestParam("itemId") Long itemNo,
                                 @RequestParam("count") int count,
                                 @AuthenticationPrincipal UserPrincipal userPrincipal,
                                 Model model){

        Item item = itemService.findById(itemNo);
        model.addAttribute("item", item);
        model.addAttribute("count", count);

        Long userNo = userPrincipal.getUser().getId();
        orderService.order(userNo, itemNo, count);
        return "order/orderCompleted";
    }
}
