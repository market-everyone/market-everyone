package web.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import web.board.controller.dto.BoardResponseDTO;
import web.board.domain.Board;
import web.board.domain.Type;
import web.board.service.BoardService;
import web.common.dto.PageRequestDTO;
import web.common.dto.PageResultDTO;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/notice")
    public String noticePage(PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("result", boardService.getList(Type.NOTICE, pageRequestDTO));
        return "service/notice";
    }

    @GetMapping("/faq")
    public String faqPage(PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("result", boardService.getList(Type.FAQ, pageRequestDTO));
        return "service/faq";
    }

    @GetMapping("/notice/{id}")
    public String noticeDetailPage(@PathVariable Long id,
                                   Model model) {
        model.addAttribute("notice", boardService.findById(id));
        return "service/noticeDetail";
    }

    @GetMapping("/faq/{id}")
    public String faqDetailPage(@PathVariable Long id,
                                Model model) {
        model.addAttribute("faq", boardService.findById(id));
        return "service/faqDetail";
    }
}
