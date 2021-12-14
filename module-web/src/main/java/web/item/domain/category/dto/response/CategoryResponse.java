package web.item.domain.category.dto.response;

import lombok.*;
import web.item.domain.category.Category;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class CategoryResponse {
    private Long id;
    private String name;
    private String imagePath;

    public static CategoryResponse of(Category category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .imagePath(category.getImagePath())
                .build();
    }
}
