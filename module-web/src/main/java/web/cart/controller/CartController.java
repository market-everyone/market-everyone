package web.cart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import web.cart.controller.dto.request.CartSaveRequest;
import web.cart.service.CartService;
import web.security.UserPrincipal;
import web.user.domain.User;

@RequiredArgsConstructor
@Controller
public class CartController {

    private final CartService cartService;

    @GetMapping("/carts")
    public String getCartList(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        cartService.findByUserId(userPrincipal.getUser());
        return "";
    }

    @PostMapping("/carts/save")
    public String save(@ModelAttribute CartSaveRequest cartSaveRequest,
                       @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();
        cartService.save(cartSaveRequest, user);
        return "redirect:";
    }

    @DeleteMapping("/carts/remove/{id}")
    public String delete(@PathVariable("id") Long itemId,
                         @AuthenticationPrincipal UserPrincipal userPrincipal) {
        User user = userPrincipal.getUser();
        cartService.delete(itemId, user);
        return "";
    }
}
