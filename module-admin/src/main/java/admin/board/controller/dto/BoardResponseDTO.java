package admin.board.controller.dto;

import lombok.Getter;
import web.board.domain.Board;
import web.board.domain.Type;
import web.user.domain.User;

@Getter
public class BoardResponseDTO {

    private final Long id;
    private final User user;
    private final String title;
    private final String content;
    private final Type type;
    private final String createDate;
    private final String modifiedDate;

    public BoardResponseDTO(Board entity) {
        id = entity.getId();
        user = entity.getUser();
        title = entity.getTitle();
        content = entity.getContent();
        type = entity.getType();
        createDate = toStringDateTime(entity.getCreateDate());
        modifiedDate = toStringDateTime(entity.getModifiedDate());
    }

    private String toStringDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Optional.ofNullable(localDateTime)
                .map(formatter::format)
                .orElse("");
    }
}