package web.item.domain.category.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.item.domain.category.Category;
import web.item.domain.category.dto.response.CategoryResponse;
import web.item.domain.category.service.CategoryService;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/categories")
@RestController
public class CategoryRestController {

    private final CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<List<CategoryResponse>> findAllCategories() {
        List<CategoryResponse> categoryResponses = categoryService.findAllCategories();
        return ResponseEntity.ok(categoryResponses);
    }
}
