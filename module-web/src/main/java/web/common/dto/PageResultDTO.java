package web.common.dto;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@Data
public class PageResultDTO<DTO, EN> {

    private List<DTO> dtoList;
    private int totalPage; // 총 페이지 번호
    private int page; // 현재 페이지 번호
    private int size; // 목록 사이즈

    private int start; // 시작 페이지 번호
    private int end; // 끝 페이지 번호

    private boolean prev;
    private boolean next;

    private List<Integer> pageList; // 페이지 번호 목록

    public PageResultDTO(Page<EN> result, Function<EN, DTO> fn) {
        dtoList = result.stream()
                .map(fn)
                .collect(toList());
        totalPage = result.getTotalPages();
        makePageList(result.getPageable());
    }

    private void makePageList(Pageable pageable) {
        this.page = pageable.getPageNumber() + 1; // 쿼리로부터 생성된 page넘버는 0부터 시작하므로 1을 추가
        this.size = pageable.getPageSize();

        // temp end page
        int tempEnd = (int) Math.ceil(page / 10.0) * 10;
        start = tempEnd - 9;
        end = Math.min(totalPage, tempEnd);


        prev = start > 1;
        next = totalPage > tempEnd;

        pageList = IntStream.rangeClosed(start, end)
                .boxed()
                .collect(toList());
    }
}
