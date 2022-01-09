package web.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.review.controller.dto.request.ReviewSaveRequest;
import web.review.controller.dto.response.ReviewResponse;
import web.review.service.ReviewService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/reviews/items/{id}")
    public String findReviewsByItemId(@PathVariable("id") Long itemId, Model model) {
        List<ReviewResponse> reviewResponses = reviewService.findReviewsByItemId(itemId);
        model.addAttribute("reviews", reviewResponses);
        return "";
    }

    @PostMapping("/reviews/save")
    public String save(@ModelAttribute ReviewSaveRequest reviewSaveRequest) {
        reviewService.save(reviewSaveRequest);
        return "redirect:/";
    }

    @DeleteMapping("/reviews/delete")
    public String delete() {
        return "";
    }
}
