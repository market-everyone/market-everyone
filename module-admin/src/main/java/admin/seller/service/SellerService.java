package admin.seller.service;

import admin.common.dto.PageRequestDTO;
import admin.common.dto.PageResultDTO;
import admin.seller.controller.dto.SellerRequestDTO;
import admin.seller.controller.dto.SellerResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.seller.domain.Seller;
import web.seller.domain.SellerRepository;
import web.seller.domain.SellerStatus;

import java.util.function.Function;

import static web.seller.domain.SellerStatus.APPROVAL;
import static web.seller.domain.SellerStatus.WAIT;

@RequiredArgsConstructor
@Service
public class SellerService {

    private final SellerRepository sellerRepository;

    @Transactional
    public Long save(SellerRequestDTO dto) {
        return sellerRepository.save(dto.toEntity()).getId();
    }

    public PageResultDTO<SellerResponseDTO, Seller> approveSellers(PageRequestDTO requestDTO) {
        return getList(APPROVAL, requestDTO);
    }

    public PageResultDTO<SellerResponseDTO, Seller> waitSellers(PageRequestDTO requestDTO) {
        return getList(WAIT, requestDTO);
    }

    @Transactional(readOnly = true)
    public PageResultDTO<SellerResponseDTO, Seller> getList(SellerStatus status, PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("SELLER_NO"));
        Page<Seller> result = sellerRepository.findApprovalSellers(status, pageable);
        Function<Seller, SellerResponseDTO> fn = (SellerResponseDTO::new);
        return new PageResultDTO<>(result, fn);
    }

    @Transactional
    public void deleteSeller(Long id) {
        sellerRepository.deleteById(id);
    }

    public SellerResponseDTO findSeller(Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new IllegalStateException("요청정보가 존재하지 않습니다."));
        return new SellerResponseDTO(seller);
    }

    @Transactional
    public void approveSeller(Long sellerId) {
        Seller seller = sellerRepository.findById(sellerId)
                .orElseThrow(() -> new IllegalStateException("요청정보가 존재하지 않습니다."));
        seller.approved();
    }
}
