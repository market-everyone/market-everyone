package web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ListController {
    @GetMapping("/user/goodsList")
    public String GoodsList(){
        return "/user/goodsList";
    }

    @GetMapping("/user/reviewList")
    public String ReviewList(){
        return "/user/reviewList";
    }

    @GetMapping("/user/myReviewList")
    public String MyReviewList(){
        return "/user/myReviewList";
    }

    @GetMapping("/user/myReviewWrite")
    public String MyReviewWrite(){
        return "/user/myReviewWrite";
    }
}
