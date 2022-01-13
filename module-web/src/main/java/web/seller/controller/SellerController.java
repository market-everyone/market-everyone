package web.seller.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.common.auth.SellerAuthConverter;
import web.item.controller.dto.response.ItemResponse;
import web.item.domain.Item;
import web.item.service.ItemService;
import web.order.domain.item.service.OrderItemService;
import web.seller.controller.dto.SellerLoginDTO;
import web.seller.controller.dto.SellerRequestDTO;
import web.seller.domain.Seller;
import web.seller.service.SellerService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;


@RequiredArgsConstructor
@Controller
@RequestMapping("/seller")
public class SellerController {

    private final SellerService sellerService;
    private final ItemService itemService;
    private final OrderItemService orderItemService;

    @GetMapping("/join")
    public String signUpPage(@ModelAttribute SellerRequestDTO sellerRequestDTO) {
        return "seller/join";
    }

    @PostMapping("/signUp")
    public String saveSeller(@Valid @ModelAttribute SellerRequestDTO sellerRequestDTO,
                             BindingResult bindingResult,
                             Model model,
                             HttpServletResponse res,
                             @RequestPart(value = "itemImage") MultipartFile file) throws IOException {

        if (bindingResult.hasErrors()) {
            sellerService.checkSellerSignUpRequestValidation(sellerRequestDTO, model);
            return "seller/join";
        }

        if (!sellerService.checkSellerSignUpRequestValidation(sellerRequestDTO, model)) {
            return "seller/join";
        }

        sellerService.save(sellerRequestDTO, res, file);

        return "redirect:login";
    }

    @GetMapping("/login")
    public String loginForm(@ModelAttribute SellerLoginDTO sellerLoginDTO) {
        return "seller/login";
    }

    @GetMapping("/mypage")
    public String myPage(Authentication authentication, Model model, @PageableDefault(size = 4, sort = "id") Pageable pageable) {

        Seller seller = SellerAuthConverter.findCurrentUserFromAuth(authentication);

        model.addAttribute("seller", seller);
        return "seller/mypage";
    }

    @GetMapping("/itemList")
    public String itemList(Authentication authentication, Model model, @PageableDefault(size = 4, sort = "id") Pageable pageable) {

        Seller seller = SellerAuthConverter.findCurrentUserFromAuth(authentication);
        Page<ItemResponse> items = itemService.findAllBySeller(seller.getId(), pageable);
        String path = File.separator + "images" + File.separator + seller.getId() + File.separator;

        model.addAttribute("seller", seller);
        model.addAttribute("items", items);
        model.addAttribute("path", path);

        return "seller/itemList";
    }

    @GetMapping("/static/{id}")
    public String itemStatics(@PathVariable Long id, Model model) {

        int sellCount = orderItemService.findSellCount(id);
        int quantity = itemService.findById(id).getQuantity();
        int sellPrice = orderItemService.findSellPrice(id);
        Item item = itemService.findById(id);

        model.addAttribute("sellCount", sellCount);
        model.addAttribute("sellPrice", sellPrice);
        model.addAttribute("quantity", quantity);
        model.addAttribute("itemName", item.getName());

        return "seller/itemStatics";
    }
}
