package web.review.domain;

import lombok.*;
import web.item.domain.Item;
import web.user.domain.User;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIEW_NO")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ITEM_NO")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "USEr_NO")
    private User user;

    private String title;

    private String content;

    private int star;
}
