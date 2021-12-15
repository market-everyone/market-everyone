package web.item.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import web.common.util.ImageUtil;
import web.item.controller.dto.request.ItemRequest;
import web.item.controller.dto.response.ItemResponse;
import web.item.domain.Item;
import web.item.domain.category.Category;
import web.item.domain.category.dto.request.CategoryRequest;
import web.item.domain.category.dto.response.CategoryResponse;
import web.item.domain.category.service.CategoryService;
import web.item.domain.option.ItemOption;
import web.item.domain.option.dto.request.ItemOptionListRequest;
import web.item.domain.option.service.OptionService;
import web.item.service.ItemService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@RequiredArgsConstructor
@RequestMapping("/item")
@Controller
public class ItemController {

    private final ItemService itemService;
    private final OptionService optionService;
    private final CategoryService categoryService;

    @GetMapping("/itemInsertForm")
    public String itemInsertForm(Model model) {

        model.addAttribute("categories", categoryService.findAll());
        return "seller/itemInsertForm";
    }

    @PostMapping("/itemInsert")
    public String itemInsert(@ModelAttribute CategoryRequest categoryRequest,
                                 @ModelAttribute ItemRequest itemInsertRequest,
                                 @ModelAttribute ItemOptionListRequest itemOptionListRequest,
                                 HttpServletRequest req,
                                 HttpServletResponse res,
                                 @RequestPart(value = "itemImage") MultipartFile file) throws IOException {

        ImageUtil imageUtil = new ImageUtil();
        String imagePath = imageUtil.uploadImage(req, res, file);

        Category category = categoryService.findByName(categoryRequest.getCategoryName());
        Long item_id = itemService.save(itemInsertRequest, category, imagePath);
        if (itemOptionListRequest.getItemOptionList() != null) {
            optionService.saveAll(itemOptionListRequest, itemService.findById(item_id));
        }
        return "redirect:itemList";
    }

    @PostMapping("/itemUpdateForm")
    public String itemUpdateForm(HttpServletRequest req, Model model) {

        Item item = itemService.findById(Long.valueOf(req.getParameter("id")));
        List<Category> categories = categoryService.findAll();
        List<ItemOption> itemOptions = optionService.findAllByItemId(item.getId());
        model.addAttribute("item", item);
        model.addAttribute("categories", categories);
        model.addAttribute("options", itemOptions);

        return "seller/itemUpdateForm";
    }

    @PostMapping("itemUpdate")
    public String itemUpdate(@ModelAttribute ItemRequest itemRequest,
                             @ModelAttribute ItemOptionListRequest itemOptionListRequest,
                             @ModelAttribute CategoryRequest categoryRequest,
                             HttpServletRequest req,
                             HttpServletResponse res,
                             @RequestPart(value = "itemImage", required = false) MultipartFile file) {

        ImageUtil imageUtil = new ImageUtil();
        // String imagePath = imageUtil.uploadImage(req, res, file);

        String imagePath = itemRequest.getImagePath();

        Category category = categoryService.findByName(categoryRequest.getCategoryName());
        Long itemId = itemService.update(itemRequest, category, imagePath, req.getParameter("itemId"));

        if (itemOptionListRequest.getItemOptionList() != null) {
            optionService.deleteAllByItemId(itemId);
            for (int i = 0; i < itemOptionListRequest.getItemOptionList().size(); i++) {
                System.out.println("price = " + itemOptionListRequest.getItemOptionList().get(i).getPrice());
                System.out.println("quantity = " + itemOptionListRequest.getItemOptionList().get(i).getQuantity());
            }
            optionService.saveAll(itemOptionListRequest, itemService.findById(itemId));
        } else {
            optionService.deleteAllByItemId(itemId);
        }

        return "redirect:itemList";
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

        return "redirect:itemList";
    }

    @GetMapping("/itemList")
    public String itemList(Model model) {

        List<Item> items = itemService.itemList();
        model.addAttribute("items", items);

        return "seller/itemList";
    }

    @GetMapping("/staticList")
    public String staticList() {
        return "seller/staticList";
    }

    @GetMapping("/statics")
    public String statics() {
        return "seller/statics";
    }
}
