package web.item.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import web.common.TestWithSecurity;
import web.item.controller.dto.response.ItemResponse;
import web.item.service.ItemService;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({
    ItemRestController.class
})
class ItemRestControllerTest extends TestWithSecurity {

    @MockBean
    private ItemService itemService;

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("API - 모든 상품 조회(페이지네이션)")
    @Test
    void find_Items_Page() throws Exception {
        //given
        long from = 0; int size = 12;
        List<ItemResponse> itemResponses = createItemResponses();
        given(itemService.findPagesBy(from, 12)).willReturn(itemResponses);

        //when
        ResultActions perform = mockMvc.perform(get("/api/items?from={from}&size={size}", from, size));

        //then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(12))
                .andExpect(jsonPath("$.[3].id").value(4));
    }

    @DisplayName("API - 해당 카테고리에 속하는 모든 상품 조회")
    @Test
    void find_Items_By_Category() throws Exception {
        //given
        List<ItemResponse> itemResponses = createItemResponses();
        given(itemService.findByCategoryId(1L)).willReturn(itemResponses);

        //when
        ResultActions perform = mockMvc.perform(get("/api/items/category?category={id}", 1L));

        //then
        perform.andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(12))
                .andExpect(jsonPath("$.[3].id").value(4));

    }

    protected List<ItemResponse> createItemResponses() {
        return Stream.iterate(1L, i -> i + 1)
                        .map(i -> ItemResponse.builder()
                                .id(i)
                                .name("" + i)
                                .price(i.intValue())
                                .imagePath("" + i)
                                .build()
                        )
                        .limit(12)
                        .collect(Collectors.toList());
    }
}