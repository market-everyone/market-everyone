package web.unit.inquiry.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import web.inquiry.domain.Inquiry;
import web.inquiry.domain.InquiryRepository;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class InquiryRepositoryImplTest {

    @Autowired
    private InquiryRepository inquiryRepository;

    @BeforeEach
    void setUp() {
        for (int i = 0; i < 50; i++) {
            Inquiry inquiry = Inquiry.builder()
                    .title(i + " 번째 문의 제목")
                    .content(i + " 번째 문의 내용")
                    .build();
            inquiryRepository.save(inquiry);
        }
    }

    @Test
    @Transactional
    @DisplayName("InquiryRepository 페이징 결과 조회 - 성공")
    void findPageInquiryTest() {
        // given
        Pageable pageable = PageRequest.of(1, 10, Sort.by("INQUIRY_NO"));

        //when
        Page<Inquiry> pageInquiry = inquiryRepository.findPageInquiry(pageable);

        //then
        int contentSize = pageInquiry.getContent().size();
        assertThat(contentSize).isGreaterThan(0);
    }
}