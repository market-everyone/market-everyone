package web.board.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import web.common.entity.BaseEntity;
import web.user.domain.User;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_NO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_NO")
    private User user;

    private String title;
    private String content;

    @Enumerated(value = EnumType.STRING)
    private Type type;

    @Builder
    public Board(User user, String title, String content, Type type) {
        this.user = user;
        this.title = title;
        this.content = content;
        this.type = type;
    }
}
