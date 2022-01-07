package admin.inquiry.controller.dto;

import lombok.Getter;
import web.inquiry.domain.Answer;
import web.inquiry.domain.Inquiry;
import web.inquiry.domain.InquiryType;
import web.user.domain.User;

import static web.util.CustomStringUtils.toStringDateTime;

@Getter
public class InquiryResponseDTO {

    private final Long id;
    private final User user;
    private final InquiryType type;
    private final Answer answer;
    private final String title;
    private final String content;
    private final String imagePath;
    private final String createDate;
    private final String modifiedDate;

    public InquiryResponseDTO(Inquiry entity) {
        id = entity.getId();
        user = entity.getUser();
        type = entity.getType();
        answer = entity.getAnswer();
        title = entity.getTitle();
        content = entity.getContent();
        imagePath = entity.getImagePath();
        createDate = toStringDateTime(entity.getCreateDate());
        modifiedDate = toStringDateTime(entity.getModifiedDate());
    }
}
