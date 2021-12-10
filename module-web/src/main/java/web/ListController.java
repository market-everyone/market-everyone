package web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ListController {
    @GetMapping("goodsList")
    public String GoodsList(){
        return "goodsList";
    }

    @GetMapping("reviewList")
    public String ReviewList(){
        return "reviewList";
    }

    @GetMapping("myReviewList")
    public String MyReviewList(){
        return "myReviewList";
    }

    @GetMapping("myReviewWrite")
    public String MyReviewWrite(){
        return "myReviewWrite";
    }

    @GetMapping("myInfo")
    public String MyInfo(){
        return "myInfo";
    }
}
