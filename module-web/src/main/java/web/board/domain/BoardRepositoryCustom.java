package web.board.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardRepositoryCustom {
    Page<Board> findPageBoard(Pageable pageable);
}
