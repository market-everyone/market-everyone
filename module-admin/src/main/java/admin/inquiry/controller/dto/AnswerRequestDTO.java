package admin.inquiry.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import web.inquiry.domain.Answer;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class AnswerRequestDTO {

    private String username;
    private String content;

    public Answer toEntity() {
        return Answer.builder()
                .username(username)
                .content(content)
                .build();
    }
}
