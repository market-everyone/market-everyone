package web.board.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import web.board.domain.Board;
import web.board.domain.Type;
import web.user.domain.User;

@Getter
@NoArgsConstructor
public class BoardRequestDTO {

    private User user;
    private String title;
    private String content;
    private Type type;

    @Builder
    public BoardRequestDTO(User user, String title, String content, Type type) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.type = type;
    }

    public Board toEntity() {
        return Board.builder()
                .user(user)
                .title(title)
                .content(content)
                .type(type)
                .build();
    }
}
