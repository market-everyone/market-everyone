package web.review.controller.dto.response;

import lombok.*;
import web.review.domain.Review;

@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReviewResponse {
    private String title;
    private String content;
    private int star;

    public static ReviewResponse of(Review review) {
        return ReviewResponse.builder()
                .title(review.getTitle())
                .content(review.getContent())
                .star(review.getStar())
                .build();
    }
}
