package web.review.controller.dto.request;

import lombok.*;
import web.review.domain.Review;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReviewSaveRequest {
    private String title;
    private String content;
    private int star;

    public Review toReview() {
        return Review.builder()
                .title(title)
                .content(content)
                .star(star)
                .build();
    }
}
