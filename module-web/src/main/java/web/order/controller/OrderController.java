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

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("order")
public class OrderController {

    private final OrderService orderService;
    private final ItemService itemService;

    @GetMapping(value = "/order")
    public String createForm(Model model){

        List<Item> items = itemService.itemList();
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

    // 주문 폼 이동
    @PostMapping("/orderForm")
    public String createOrderForm(@RequestParam("itemId") Long id, Model model){
        Item item = itemService.findById(id);
        model.addAttribute("item", item);
        return "order/orderForm";
    }

    // 주문 완료
    @PostMapping("/orderCompleted")
    public String orderComp(@RequestParam("itemId") Long itemNo,
                            @AuthenticationPrincipal UserPrincipal userPrincipal,
                            Model model){

        Item item = itemService.findById(itemNo);
        model.addAttribute("item", item);

        Long userNo = userPrincipal.getUser().getId();
        orderService.order(userNo, itemNo, 1);

        return "order/orderCompleted";
    }

}
