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

        model.addAttribute("categoryList", categoryService.findAll());
        return "seller/itemInsertForm";
    }

    @PostMapping("/itemInsert")
    public String itemInsert(@ModelAttribute CategoryRequest categoryRequest,
                             @ModelAttribute ItemRequest itemInsertRequest,
                             @ModelAttribute ItemOptionListRequest itemOptionListRequest,
                             HttpServletRequest req,
                             HttpServletResponse res,
                             @RequestPart(value = "itemImage") MultipartFile file) throws IOException {

        Category category = categoryService.findByName(categoryRequest.getCategoryName());
        Long item_id = itemService.save(itemInsertRequest, category, req, res, file);
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
        model.addAttribute("categoryList", categories);
        model.addAttribute("options", itemOptions);

        return "seller/itemUpdateForm";
    }

    @PostMapping("itemUpdate")
    public String itemUpdate(@ModelAttribute ItemRequest itemRequest,
                             @ModelAttribute ItemOptionListRequest itemOptionListRequest,
                             @ModelAttribute CategoryRequest categoryRequest,
                             HttpServletRequest req,
                             HttpServletResponse res,
                             @RequestPart(value = "itemImage", required = false) MultipartFile file) throws IOException {

        Long id = Long.valueOf(req.getParameter("itemId"));
        Category category = categoryService.findByName(categoryRequest.getCategoryName());
        Long itemId = itemService.update(itemRequest, category, req, res, file, id);
        if (itemOptionListRequest.getItemOptionList() != null) {
            optionService.deleteAllByItemId(itemId);
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

    @GetMapping("/{id}")
    public String itemDetail(@PathVariable Long id, Model model) {

        Item item = itemService.findById(id);
        List<ItemOption> options = optionService.findAllByItemId(id);
        if (options.size() != 0) {
            model.addAttribute("options", options);
        }
        model.addAttribute("item", item);
        return "item/itemDetail";
    }

    @PostMapping("/ckImage")
    @ResponseBody
    public String imageUpload(@RequestPart(value = "file", required = false) MultipartFile file) throws Exception {
        System.out.println("작동");

        return "";
    }
}
