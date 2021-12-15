package web.item.domain.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.item.domain.category.Category;
import web.item.domain.category.CategoryRepository;
import web.item.domain.category.dto.response.CategoryResponse;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> findAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryResponse::of)
                .collect(Collectors.toList());
    }

    public Category findByName(String name) {
        return categoryRepository.findByName(name);
    }

    public CategoryResponse findById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow();
        return CategoryResponse.of(category);
    }
}
