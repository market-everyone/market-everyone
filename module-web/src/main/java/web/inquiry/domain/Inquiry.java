package web.inquiry.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import web.common.entity.BaseEntity;
import web.user.domain.User;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Inquiry extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INQUIRY_NO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_NO")
    private User user;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "ANSWER_NO")
    private Answer answer;

    @Enumerated(value = EnumType.STRING)
    private InquiryType type;

    private String title;
    private String content;
    private String imagePath;

    @Builder
    public Inquiry(Long id, User user, Answer answer, InquiryType type, String title, String content, String imagePath) {
        this.id = id;
        this.user = user;
        this.answer = answer;
        this.type = type;
        this.title = title;
        this.content = content;
        this.imagePath = imagePath;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
        answer.setInquiry(this);
    }
}
