package admin.seller.service;

import admin.common.dto.PageRequestDTO;
import admin.common.dto.PageResultDTO;
import admin.seller.controller.dto.SellerRequestDTO;
import admin.seller.controller.dto.SellerResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import web.seller.domain.Seller;
import web.seller.domain.SellerRepository;
import web.seller.domain.SellerStatus;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SellerServiceTest {


    @Autowired
    private SellerService sellerService;

    @Autowired
    private SellerRepository sellerRepository;

    @Test
    @DisplayName("리스트 불러오기")
    @Transactional
    void testList() {
        //given
        IntStream.rangeClosed(1, 100).forEach(i -> {
            SellerRequestDTO dto = SellerRequestDTO.builder()
                    .sellerStatus(SellerStatus.APPROVAL)
                    .brandName("테스트 브랜드명" + i)
                    .brandContent("테스트 브랜드 내용" + i)
                    .itemContent("테스트 취급 상품 설명" + i)
                    .build();
            sellerService.save(dto);
        });
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();

        //when
        PageResultDTO<SellerResponseDTO, Seller> pageResultDTO = sellerService.getList(pageRequestDTO);

        //then
        assertThat(pageResultDTO.getDtoList()).isNotEmpty();
    }

    @Test
    @DisplayName("게시글 삭제")
    @Transactional
    void deleteTest() {
        //given
        SellerRequestDTO dto = SellerRequestDTO.builder()
                .sellerStatus(SellerStatus.APPROVAL)
                .brandName("테스트 브랜드명")
                .brandContent("테스트 브랜드 내용")
                .itemContent("테스트 취급 상품 설명")
                .build();
        Long sellerId = sellerService.save(dto);

        //when
        sellerService.deleteSeller(sellerId);

        //then
        Optional<Seller> findSeller = sellerRepository.findById(sellerId);
        assertThat(findSeller).isEmpty();
    }
}