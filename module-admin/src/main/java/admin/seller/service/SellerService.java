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

import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class SellerService {

    private final SellerRepository sellerRepository;

    @Transactional
    public Long save(SellerRequestDTO dto) {
        return sellerRepository.save(dto.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public PageResultDTO<SellerResponseDTO, Seller> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("SELLER_NO"));
        Page<Seller> result = sellerRepository.findApprovalSellers(pageable);
        Function<Seller, SellerResponseDTO> fn = (entity -> new SellerResponseDTO(entity));
        return new PageResultDTO<>(result, fn);
    }
}
