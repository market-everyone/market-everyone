package web.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.review.controller.dto.request.ReviewSaveRequest;
import web.review.controller.dto.response.ReviewResponse;
import web.review.domain.Review;
import web.review.domain.ReviewRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public Review save(ReviewSaveRequest reviewSaveRequest) {
        Review review = reviewSaveRequest.toReview();
        return reviewRepository.save(review);
    }

    @Transactional(readOnly = true)
    public List<ReviewResponse> findReviewsByItemId(Long itemId) {
        return reviewRepository.findAllByItemId(itemId)
                .stream()
                .map(ReviewResponse::of)
                .collect(Collectors.toList());
    }
}
