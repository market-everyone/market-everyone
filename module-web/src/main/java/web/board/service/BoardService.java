package web.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.board.controller.dto.BoardRequestDTO;
import web.board.controller.dto.BoardResponseDTO;
import web.board.domain.Board;
import web.board.domain.BoardRepository;
import web.board.domain.Type;
import web.common.dto.PageRequestDTO;
import web.common.dto.PageResultDTO;
import web.exception.board.BoardNotFoundException;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional
    public Long save(BoardRequestDTO requestDTO) {
        return boardRepository.save(requestDTO.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public PageResultDTO<BoardResponseDTO, Board> getList(Type type, PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("id"));
        Page<Board> result = boardRepository.findAllByType(type, pageable);
        return new PageResultDTO<>(result, BoardResponseDTO::new);
    }

    public BoardResponseDTO findById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(BoardNotFoundException::new);

        return BoardResponseDTO.of(board);
    }
}
