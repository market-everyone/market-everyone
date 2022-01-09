package web.inquiry.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import web.user.domain.User;

public interface InquiryRepository extends JpaRepository<Inquiry, Long>, InquiryRepositoryCustom {
    void deleteByUser(User user);
}