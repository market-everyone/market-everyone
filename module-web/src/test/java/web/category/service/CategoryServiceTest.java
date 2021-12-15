package web.category.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import web.item.domain.category.Category;
import web.item.domain.category.CategoryRepository;
import web.item.domain.category.dto.response.CategoryResponse;
import web.item.domain.category.service.CategoryService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    void findAll() {
    }

    @DisplayName("모든 카테고리 조회")
    @Test
    void find_All_Categories() {
        //given
        List<Category> categories = createCategoryEntities();
        given(categoryRepository.findAll()).willReturn(categories);

        //when
        List<CategoryResponse> categoryResponses = categoryService.findAllCategories();

        //then
        assertSame(categoryResponses.size(), 10);
    }

    @Test
    void findByName() {
    }

    @DisplayName("단일 카테고리 조회")
    @Test
    void find_Category_By_Id() {
        //given
        Category category = createCategoryEntity();
        given(categoryRepository.findById(1L)).willReturn(Optional.ofNullable(category));

        //when
        CategoryResponse categoryResponse = categoryService.findById(1L);

        //then
        assertSame(categoryResponse.getId(), 1L);
        assertSame(categoryResponse.getName(), "테스트이름");
    }

    protected List<Category> createCategoryEntities() {
        return Stream.iterate(1L, i -> i + 1)
                .map(i -> Category.builder()
                        .id(i)
                        .name("" + i)
                        .imagePath("" + i)
                        .build())
                .limit(10)
                .collect(Collectors.toList());
    }

    protected Category createCategoryEntity() {
        return Category.builder()
                .id(1L)
                .name("테스트이름")
                .imagePath("테스트이미지")
                .build();
    }

}