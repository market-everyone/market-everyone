package web.inquiry.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import web.common.entity.BaseEntity;

import javax.persistence.*;

import static web.util.CustomStringUtils.toStringDateTime;

@Getter
@NoArgsConstructor
@Entity
public class Answer extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ANSWER_NO")
    private Long id;

    @OneToOne(mappedBy = "answer")
    private Inquiry inquiry;

    private String username;

    private String content;

    @Builder
    public Answer(Long id, Inquiry inquiry, String username, String content) {
        this.id = id;
        this.inquiry = inquiry;
        this.username = username;
        this.content = content;
    }

    public void setInquiry(Inquiry inquiry) {
        this.inquiry = inquiry;
    }

    public void update(String content) {
        this.content = content;
    }

    public String getProxyCreateDate() {
        return toStringDateTime(super.getCreateDate());
    }

    public String getProxyModifiedDate() {
        return toStringDateTime(super.getModifiedDate());
    }
}
