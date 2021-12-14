package web.item.domain.category.dto.request;

import lombok.*;
import web.item.domain.category.Category;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CategoryRequest {

    private String categoryName;


    public Category toCategory(String categoryName) {
        return Category.builder()
                .name(categoryName)
                .build();
    }

}
