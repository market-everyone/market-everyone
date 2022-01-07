package web.seller.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import web.seller.controller.dto.SellerLoginDTO;
import web.seller.controller.dto.SellerRequestDTO;
import web.seller.domain.Seller;
import web.seller.service.SellerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@RequiredArgsConstructor
@Controller
@RequestMapping("/seller")
public class SellerController {

    private final SellerService sellerService;

    @GetMapping("/join")
    public String signUpPage(@ModelAttribute SellerRequestDTO sellerRequestDTO) {
        return "seller/join";
    }

    @PostMapping("/signUp")
    public String saveSeller(@Valid @ModelAttribute SellerRequestDTO sellerRequestDTO,
                             BindingResult bindingResult,
                             Model model) {
        Seller seller = new Seller();
        if (bindingResult.hasErrors()) {
            sellerService.checkSellerSignUpRequestValidation(sellerRequestDTO, model);
            return "seller/join";
        }

        if (!sellerService.checkSellerSignUpRequestValidation(sellerRequestDTO, model)) {
            return "seller/join";
        }

        sellerService.save(sellerRequestDTO);

        return "redirect:login";
    }

    @GetMapping("/login")
    public String loginForm(@ModelAttribute SellerLoginDTO sellerLoginDTO) {
        return "seller/login";
    }

}
