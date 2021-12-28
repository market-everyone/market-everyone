package admin.inquiry.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web.inquiry.domain.Answer;
import web.inquiry.domain.Inquiry;
import web.inquiry.domain.InquiryType;
import web.user.domain.User;

@Getter
@NoArgsConstructor
@Setter
public class InquiryRequestDTO {

    private User user;
    private String title;
    private String content;
    private Answer answer;
    private InquiryType type;
    private String imagePath;

    @Builder
    public InquiryRequestDTO(User user, String title, String content, Answer answer, InquiryType type, String imagePath) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.answer = answer;
        this.type = type;
        this.imagePath = imagePath;
    }

    public Inquiry toEntity() {
        return Inquiry.builder()
                .user(user)
                .title(title)
                .content(content)
                .answer(answer)
                .type(type)
                .imagePath(imagePath)
                .build();
    }
}
