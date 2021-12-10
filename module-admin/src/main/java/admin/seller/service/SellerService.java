package admin.seller.service;

import admin.common.dto.PageRequestDTO;
import admin.common.dto.PageResultDTO;
import admin.seller.controller.dto.SellerDTO;
import web.seller.domain.Seller;

public interface SellerService {

    Seller register(SellerDTO dto);

    PageResultDTO<SellerDTO, Seller> getList(PageRequestDTO requestDTO);

    default Seller dtoToEntity(SellerDTO dto) {
        return Seller.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .brandName(dto.getBrandName())
                .brandContent(dto.getBrandContent())
                .itemContent(dto.getItemContent())
                .imagePath(dto.getImagePath())
                .sellerStatus(dto.getSellerStatus())
                .build();
    }

    default SellerDTO entityToDto(Seller entity) {
        return SellerDTO.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .password(entity.getPassword())
                .brandName(entity.getBrandName())
                .brandContent(entity.getBrandContent())
                .itemContent(entity.getItemContent())
                .imagePath(entity.getImagePath())
                .regDate(entity.getRegDate())
                .modDate(entity.getModDate())
                .sellerStatus(entity.getSellerStatus())
                .build();
    }
}
