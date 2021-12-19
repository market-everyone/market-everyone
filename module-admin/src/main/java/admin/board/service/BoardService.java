package admin.board.service;

import admin.board.controller.dto.BoardRequestDTO;
import admin.board.controller.dto.BoardResponseDTO;
import admin.common.dto.PageRequestDTO;
import admin.common.dto.PageResultDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.board.domain.Board;
import web.board.domain.BoardRepository;

import java.util.function.Function;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    @Transactional(readOnly = true)
    public BoardResponseDTO findBoard(Long boardId) {
        Board findBoard = boardRepository.findById(boardId)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 게시글이 없습니다."));
        return new BoardResponseDTO(findBoard);
    }

    @Transactional
    public Long save(BoardRequestDTO requestDTO) {
        return boardRepository.save(requestDTO.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    public PageResultDTO<BoardResponseDTO, Board> getList(PageRequestDTO requestDTO) {
        Pageable pageable = requestDTO.getPageable(Sort.by("BOARD_NO"));
        Page<Board> result = boardRepository.findPageBoard(pageable);
        Function<Board, BoardResponseDTO> fn = (BoardResponseDTO::new);
        return new PageResultDTO<>(result, fn);
    }

    @Transactional
    public Long update(Long boardId, BoardRequestDTO boardRequestDTO) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 게시글이 없습니다."));
        board.update(boardRequestDTO.getTitle(),
                boardRequestDTO.getContent(),
                boardRequestDTO.getType());
        return boardId;
    }
}
