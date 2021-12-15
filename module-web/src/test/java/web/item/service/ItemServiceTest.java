package web.item.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import web.item.controller.dto.response.ItemResponse;
import web.item.domain.Item;
import web.item.domain.ItemRepository;
import web.item.domain.category.Category;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    @DisplayName("해당 카테고리에 속하는 모든 상품 조회")
    @Test
    void find_Item_By_CategoryId() {
        //given
        Category category = createCategory();
        List<Item> items = createItemEntities();
        given(itemRepository.findByCategoryId(category.getId())).willReturn(items);

        //when
        List<ItemResponse> itemResponses = itemService.findByCategoryId(category.getId());

        //then
        assertSame(itemResponses.size(), 12);
    }

    protected List<Item> createItemEntities() {
        final Category category = createCategory();

        return Stream.iterate(1L, i -> i + 1)
                .map(i -> Item.builder()
                        .id(i)
                        .category(category)
                        .price(i.intValue())
                        .name("테스트이름" + i)
                        .content("테스트컨텐트" + i)
                        .imagePath("테스트이미지" + i)
                        .build()
                )
                .limit(12)
                .collect(Collectors.toList());
    }

    protected Item createItemEntity() {
        final Category category = createCategory();

        return Item.builder()
                .id(1L)
                .category(category)
                .price(1)
                .name("테스트이름")
                .content("테스트컨텐트")
                .imagePath("테스트이미지")
                .build();
    }

    private Category createCategory() {
        return Category.builder()
                .id(1L)
                .name("테스트카테고리")
                .build();
    }
}