package admin.inquiry.service;

import admin.common.dto.PageRequestDTO;
import admin.common.dto.PageResultDTO;
import admin.inquiry.controller.dto.AnswerRequestDTO;
import admin.inquiry.controller.dto.InquiryRequestDTO;
import admin.inquiry.controller.dto.InquiryResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import web.inquiry.domain.Answer;
import web.inquiry.domain.Inquiry;
import web.inquiry.domain.InquiryRepository;
import web.inquiry.domain.InquiryType;
import web.user.domain.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class InquiryServiceTest {

    @InjectMocks
    private InquiryService inquiryService;

    @Mock
    private InquiryRepository inquiryRepository;

    @Test
    @DisplayName("1:1문의글 저장 - 성공")
    void save() {
        //given
        User user = createUser();
        InquiryRequestDTO requestDTO = createInquiryRequestDTO(user);
        Inquiry entity = createInquiryEntity(user);
        given(inquiryRepository.save(any())).willReturn(entity);

        //when
        Long saveId = inquiryService.save(requestDTO);

        //then
        assertThat(saveId).isEqualTo(entity.getId());
    }

    @Test
    @DisplayName("1:1문의글 페이징 - 성공")
    void findPageInquiryTest() {
        //given
        int pageSize = 10;
        PageRequestDTO pageRequestDTO = new PageRequestDTO(1, pageSize);
        Page<Inquiry> pageInquiry = createPageImpl(pageSize, pageRequestDTO);
        given(inquiryRepository.findPageInquiry(any())).willReturn(pageInquiry);

        //when
        PageResultDTO<InquiryResponseDTO, Inquiry> pageResultDTO =
                inquiryService.findPageInquiry(pageRequestDTO);

        //then
        assertThat(pageResultDTO.getSize()).isEqualTo(pageSize);
    }

    @Test
    @DisplayName("1:1문의글 답변 저장 - 성공")
    void answerToInquiry() {
        //given
        Long answerId = 11L;
        String answerContent = "답변";
        User admin = createUser();
        User client = createUser();
        client.setId(2L);
        Inquiry inquiry = createInquiryEntity(client);
        Answer answer = createAnswer(answerId, answerContent, admin);
        inquiry.setAnswer(answer);
        //Repository - inquiry stub
        given(inquiryRepository.findById(any())).willReturn(Optional.ofNullable(inquiry));
        //AnswerRequestDto
        AnswerRequestDTO answerRequestDTO = new AnswerRequestDTO(admin.getName(), answerContent);

        //when
        Long inquiryId = inquiryService.answerToInquiry(inquiry.getId(), answerRequestDTO);

        //then
        assertAll(
                () -> assertThat(inquiryId).isEqualTo(inquiry.getId()),
                () -> assertThat(inquiry.getAnswer().getContent()).isEqualTo(answerRequestDTO.getContent())
        );
    }

    @Test
    @DisplayName("1:1문의글 단일 조회 - 성공")
    void findOneInquiryTest() {
        //given
        User user = createUser();
        InquiryRequestDTO inquiryRequestDTO = createInquiryRequestDTO(user);
        Answer answer = createAnswer(1L, "답변 내용", user);
        Inquiry inquiry = createInquiryEntity(user);
        inquiry.setAnswer(answer);
        given(inquiryRepository.findById(inquiry.getId())).willReturn(Optional.of(inquiry));

        //when
        InquiryResponseDTO inquiryResponseDTO = inquiryService.findInquiryById(inquiry.getId());

        //then
        assertAll(
                () -> assertThat(inquiryResponseDTO.getAnswer().getId()).isEqualTo(answer.getId()),
                () -> assertThat(inquiryResponseDTO.getId()).isEqualTo(inquiry.getId())
        );
    }

    private PageImpl createPageImpl(int pageSize, PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable(Sort.by(Sort.Direction.DESC, "INQUIRY_NO"));
        List<Inquiry> content = new ArrayList<>();
        User user = createUser();
        LongStream.rangeClosed(1, pageSize).forEach(i -> {
            Inquiry inquiry = Inquiry.builder()
                    .id(i)
                    .title("title")
                    .content("content")
                    .user(user)
                    .build();
            content.add(inquiry);
        });
        PageImpl pageImpl = new PageImpl(content, pageable, pageSize);
        return pageImpl;
    }

    private Inquiry createInquiryEntity(User user) {
        Inquiry entity = Inquiry.builder()
                .id(1L)
                .user(user)
                .type(InquiryType.EXCHANGE)
                .title("문의글 제목")
                .content("문의글 내용")
                .imagePath("/var/image")
                .build();
        return entity;
    }

    private InquiryRequestDTO createInquiryRequestDTO(User user) {
        InquiryRequestDTO requestDTO = InquiryRequestDTO.builder()
                .user(user)
                .content("content")
                .title("title")
                .build();
        return requestDTO;
    }

    private User createUser() {
        return User.builder()
                .id(1L)
                .email("test@test.com")
                .name("test")
                .build();
    }

    private Answer createAnswer(Long answerId, String answerContent, User admin) {
        Answer answer = Answer.builder()
                .id(answerId)
                .content(answerContent)
                .username(admin.getName())
                .build();
        return answer;
    }
}
