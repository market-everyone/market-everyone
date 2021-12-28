package web.inquiry.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InquiryRepositoryCustom {
    Page<Inquiry> findPageInquiry(Pageable pageable);
}
