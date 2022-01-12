package web.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.item.domain.Item;
import web.item.service.ItemService;
import web.order.domain.Order;
import web.order.service.OrderService;
import web.review.controller.dto.request.ReviewSaveRequest;
import web.review.controller.dto.response.ReviewResponse;
import web.review.service.ReviewService;
import web.security.UserPrincipal;
import web.user.domain.User;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class ReviewController {

    private final ReviewService reviewService;
    private final OrderService orderService;
    private final ItemService itemService;

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


    @GetMapping("/review/myReviewList")
    public String myReviewList(Model model){
        List<Order> orders = orderService.findOrders();
        model.addAttribute("orders", orders);
        return "review/myReviewList";
    }

    @GetMapping("/review/writeReview/{id}")
    public String insertReview(@PathVariable("id") Long id,
                               @AuthenticationPrincipal UserPrincipal userPrincipal,
                               Model model){

        Item item = itemService.findById(id);
        User user = userPrincipal.getUser();

        model.addAttribute("item", item);
        model.addAttribute("user", user);
        return "review/writeReview";
    }
}
