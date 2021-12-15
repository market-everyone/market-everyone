package web.unit.category.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import web.unit.common.TestWithSecurity;
import web.item.domain.category.controller.CategoryRestController;
import web.item.domain.category.dto.response.CategoryResponse;
import web.item.domain.category.service.CategoryService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({
    CategoryRestController.class
})
class CategoryRestControllerTest extends TestWithSecurity {

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("API - 모든 카테고리 조회")
    @Test
    void find_All_Categories() throws Exception {
        //given
        List<CategoryResponse> categoryResponses = createCategoryResponses();
        given(categoryService.findAllCategories()).willReturn(categoryResponses);

        //when
        ResultActions perform = mockMvc.perform(get("/api/categories"));

        //then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(10))
                .andExpect(jsonPath("$.[5].id").value(6));
    }

    protected List<CategoryResponse> createCategoryResponses() {
        return Stream.iterate(1L, i -> i + 1)
                .map(i -> CategoryResponse.builder()
                            .id(i)
                            .name("" + i)
                            .imagePath("" + i)
                            .build()
                )
                .limit(10)
                .collect(Collectors.toList());
    }

}