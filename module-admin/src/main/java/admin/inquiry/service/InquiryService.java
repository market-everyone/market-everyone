package admin.inquiry.service;

import admin.common.dto.PageRequestDTO;
import admin.common.dto.PageResultDTO;
import admin.inquiry.controller.dto.AnswerRequestDTO;
import admin.inquiry.controller.dto.InquiryRequestDTO;
import admin.inquiry.controller.dto.InquiryResponseDTO;
import jdk.jfr.StackTrace;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.inquiry.domain.Answer;
import web.inquiry.domain.Inquiry;
import web.inquiry.domain.InquiryRepository;

import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class InquiryService {

    private final InquiryRepository inquiryRepository;

    @Transactional(readOnly = true)
    public InquiryResponseDTO findInquiryById(Long inquiryId) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글을 찾지 못했습니다."));
        return new InquiryResponseDTO(inquiry);
    }

    @Transactional
    public Long save(InquiryRequestDTO requestDTO) {
        return inquiryRepository.save(requestDTO.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public PageResultDTO<InquiryResponseDTO, Inquiry> findPageInquiry(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by(Sort.Direction.DESC, "INQUIRY_NO"));
        Page<Inquiry> pageInquiry = inquiryRepository.findPageInquiry(pageable);
        Function<Inquiry, InquiryResponseDTO> fn = (InquiryResponseDTO::new);
        return new PageResultDTO<>(pageInquiry, fn);
    }

    @Transactional
    public Long answerToInquiry(Long inquiryId, AnswerRequestDTO answerRequestDTO) {
        Inquiry inquiry = inquiryRepository.findById(inquiryId)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 게시글을 찾지 못했습니다."));
        Answer answer = inquiry.getAnswer();
        if (answer != null) {
            answer.update(answerRequestDTO.getContent());
            inquiry.setAnswer(answer);
        } else {
            inquiry.setAnswer(answerRequestDTO.toEntity());
        }
        return inquiryId;
    }
}
