package admin.board.controller.dto;

import lombok.Getter;
import web.board.domain.Board;
import web.user.domain.User;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Getter
public class BoardResponseDTO {

    private Long id;
    private User user;
    private String title;
    private String content;
    private String createDate;
    private String modifiedDate;

    public BoardResponseDTO(Board entity) {
        id = entity.getId();
        user = entity.getUser();
        title = entity.getTitle();
        content = entity.getContent();
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