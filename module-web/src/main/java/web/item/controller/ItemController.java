package web.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.common.auth.SellerAuthConverter;
import web.common.util.ImageUtil;
import web.item.controller.dto.request.ItemRequest;
import web.item.domain.Item;
import web.item.domain.category.Category;
import web.item.domain.category.dto.request.CategoryRequest;
import web.item.domain.category.service.CategoryService;
import web.item.domain.option.ItemOption;
import web.item.domain.option.dto.request.ItemOptionListRequest;
import web.item.domain.option.service.OptionService;
import web.item.service.ItemService;
import web.seller.domain.Seller;
import web.seller.service.SellerService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;


@RequiredArgsConstructor
@RequestMapping("/item")
@Controller
public class ItemController {

    private final ItemService itemService;
    private final OptionService optionService;
    private final CategoryService categoryService;
    private final SellerService sellerService;

    @GetMapping("/itemInsertForm")
    public String itemInsertForm(Authentication authentication, Model model) {

        Seller seller = SellerAuthConverter.findCurrentUserFromAuth(authentication);

        model.addAttribute("categoryList", categoryService.findAll());
        model.addAttribute("seller", sellerService.findById(seller.getId()));
        return "item/itemInsertForm";
    }

    @PostMapping("/itemInsert")
    public String itemInsert(@ModelAttribute CategoryRequest categoryRequest,
                             @ModelAttribute ItemRequest itemInsertRequest,
                             @ModelAttribute ItemOptionListRequest itemOptionListRequest,
                             Authentication authentication,
                             HttpServletResponse res,
                             @RequestPart(value = "itemImage", required = false) MultipartFile file) throws IOException {

        Category category = categoryService.findByName(categoryRequest.getCategoryName());
        Seller seller = SellerAuthConverter.findCurrentUserFromAuth(authentication);
        Long item_id = itemService.save(itemInsertRequest, category, seller, res, file);
        if (itemOptionListRequest.getItemOptionList() != null) {
            optionService.saveAll(itemOptionListRequest, itemService.findById(item_id));
        }
        return "redirect:/seller/itemList";
    }

    @PostMapping("/itemUpdateForm")
    public String itemUpdateForm(Authentication authentication, HttpServletRequest req, Model model) {

        Item item = itemService.findById(Long.valueOf(req.getParameter("id")));
        Seller seller = SellerAuthConverter.findCurrentUserFromAuth(authentication);
        List<Category> categories = categoryService.findAll();
        List<ItemOption> itemOptions = optionService.findAllByItemId(item.getId());
        String path = File.separator + "images" + File.separator + seller.getId() + File.separator;
        model.addAttribute("item", item);
        model.addAttribute("categoryList", categories);
        model.addAttribute("options", itemOptions);
        model.addAttribute("seller", seller);
        model.addAttribute("path", path);

        return "item/itemUpdateForm";
    }

    @PostMapping("itemUpdate")
    public String itemUpdate(@ModelAttribute ItemRequest itemRequest,
                             @ModelAttribute ItemOptionListRequest itemOptionListRequest,
                             @ModelAttribute CategoryRequest categoryRequest,
                             HttpServletRequest req) throws IOException {

        Long id = Long.valueOf(req.getParameter("itemId"));
        Long itemId = itemService.update(itemRequest, id);
        if (itemOptionListRequest.getItemOptionList() != null) {
            optionService.deleteAllByItemId(itemId);
            optionService.saveAll(itemOptionListRequest, itemService.findById(itemId));
        } else {
            optionService.deleteAllByItemId(itemId);
        }

        return "redirect:/seller/itemList";
    }

    @PostMapping("itemDelete")
    public String itemDelete(HttpServletRequest req) {

        Long id = Long.valueOf(req.getParameter("id"));
        Item item = itemService.findById(id);
        ImageUtil imageUtil = new ImageUtil();
        if (item.getImagePath() != null) {
            imageUtil.deleteImage(item.getImagePath());
        }
        optionService.deleteAllByItemId(id);
        itemService.delete(item);

        return "redirect:/seller/itemList";
    }

    @GetMapping("/{id}")
    public String itemDetail(@PathVariable Long id, Model model) {

        Item item = itemService.findById(id);
        Seller seller = sellerService.findById(item.getSeller().getId());
        List<ItemOption> options = optionService.findAllByItemId(id);
        if (options.size() != 0) {
            model.addAttribute("options", options);
        }
        model.addAttribute("item", item);
        model.addAttribute("seller", seller);
        return "item/itemDetail";
    }
}
