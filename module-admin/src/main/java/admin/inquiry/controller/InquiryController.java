package admin.inquiry.controller;

import admin.common.dto.PageRequestDTO;
import admin.inquiry.controller.dto.AnswerRequestDTO;
import admin.inquiry.controller.dto.InquiryResponseDTO;
import admin.inquiry.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.inquiry.domain.Inquiry;
import web.user.domain.User;

@RequiredArgsConstructor
@Controller
@RequestMapping("/inquiry")
public class InquiryController {

    private final InquiryService inquiryService;

    @GetMapping
    public String inquiryList(PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("result", inquiryService.findPageInquiry(pageRequestDTO));
        return "inquiry/inquiry_list";
    }

    @GetMapping("/{inquiryId}")
    public String inquiry(@PathVariable long inquiryId, Model model) {
        InquiryResponseDTO inquiry = inquiryService.findInquiryById(inquiryId);
        AnswerRequestDTO answer = new AnswerRequestDTO();
        model.addAttribute("inquiry", inquiry);
        model.addAttribute("answer", answer);
        return "inquiry/inquiry";
    }

    @PostMapping("/{inquiryId}/answer")
    public String answerToInquiry(@PathVariable long inquiryId,
                                  @ModelAttribute(value = "answer") AnswerRequestDTO answer,
                                  @AuthenticationPrincipal User user,
                                  RedirectAttributes redirectAttributes) {
        answer.setUsername(user.getName());
        inquiryService.answerToInquiry(inquiryId, answer);
        redirectAttributes.addAttribute("inquiryId", inquiryId);
        return "redirect:/inquiry/{inquiryId}";
    }
}
