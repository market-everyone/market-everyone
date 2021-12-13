package admin.seller.controller;

import admin.common.dto.PageRequestDTO;
import admin.seller.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/seller")
public class SellerController {

    private final SellerService sellerService;

    @GetMapping("/")
    public String index() {
        return "redirect:/seller/list";
    }

    @GetMapping("/list")
    public String sellers(PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("result", sellerService.getList(pageRequestDTO));
        return "seller/list";
    }

    @PostMapping("/delete")
    public String deleteSeller(Long id) {
        sellerService.deleteSeller(id);
        return "redirect:/seller/list";
    }
}
