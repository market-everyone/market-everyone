package admin.seller.service;

import admin.common.dto.PageRequestDTO;
import admin.common.dto.PageResultDTO;
import admin.seller.controller.dto.SellerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.seller.domain.Seller;
import web.seller.domain.SellerRepository;

import java.util.function.Function;

@Slf4j
@RequiredArgsConstructor
@Service
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;

    @Transactional
    @Override
    public Seller register(SellerDTO dto) {
        Seller seller = dtoToEntity(dto);
        return sellerRepository.save(seller);
    }

    @Transactional
    @Override
    public PageResultDTO<SellerDTO, Seller> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("SELLER_NO"));
        Page<Seller> result = sellerRepository.findApprovalSellers(pageable);
        Function<Seller, SellerDTO> fn = (this::entityToDto);
        return new PageResultDTO<>(result, fn);
    }
}
