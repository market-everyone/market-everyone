package admin.board.service;

import admin.board.controller.dto.BoardRequestDTO;
import admin.board.controller.dto.BoardResponseDTO;
import admin.common.dto.PageRequestDTO;
import admin.common.dto.PageResultDTO;
import admin.seller.controller.dto.SellerResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.board.domain.Board;
import web.board.domain.BoardRepository;
import web.seller.domain.Seller;
import web.seller.domain.SellerStatus;

import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Long save(BoardRequestDTO requestDTO) {
        return boardRepository.save(requestDTO.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public PageResultDTO<BoardResponseDTO, Board> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("BOARD_NO"));
        Page<Board> result = boardRepository.findAll(pageable);
        Function<Board, BoardResponseDTO> fn = (BoardResponseDTO::new);
        return new PageResultDTO<>(result, fn);
    }
}
