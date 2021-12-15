package web.item.domain.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import web.item.domain.category.dto.response.CategoryResponse;
import web.item.domain.category.service.CategoryService;
import web.item.service.ItemService;

@RequestMapping("/categories")
@RequiredArgsConstructor
@Controller
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public String categoryPage(@PathVariable Long id, Model model) {
        CategoryResponse categoryResponse = categoryService.findById(id);
        model.addAttribute("category", categoryResponse);
        return "category";
    }
}
