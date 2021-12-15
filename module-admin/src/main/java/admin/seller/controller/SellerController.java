package admin.seller.controller;

import admin.common.dto.PageRequestDTO;
import admin.seller.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/seller")
public class SellerController {

    private final SellerService sellerService;

    @GetMapping
    public String index() {
        return "redirect:/seller/sellers";
    }

    @GetMapping("/sellers")
    public String approveSellers(PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("result", sellerService.approveSellers(pageRequestDTO));
        return "seller/sellers";
    }

    @PostMapping("/delete")
    public String deleteSeller(Long id) {
        sellerService.deleteSeller(id);
        return "redirect:/seller/sellers";
    }

    @GetMapping("/wait-sellers")
    public String waitSellers(PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("result", sellerService.waitSellers(pageRequestDTO));
        return "seller/wait_sellers";
    }

    @GetMapping("/wait-sellers/{sellerId}")
    public String waitSeller(@PathVariable long sellerId, Model model) {
        model.addAttribute("seller", sellerService.findSeller(sellerId));
        return "seller/seller";
    }

    @PostMapping("/approve")
    public String approveSeller(Long id) {
        sellerService.approveSeller(id);
        return "redirect:/seller/wait-sellers";
    }
}
