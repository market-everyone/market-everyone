package admin.board.controller;

import admin.board.controller.dto.BoardRequestDTO;
import admin.board.controller.dto.BoardResponseDTO;
import admin.board.service.BoardService;
import admin.common.dto.PageRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import web.user.domain.User;

@RequiredArgsConstructor
@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public String boardList(PageRequestDTO pageRequestDTO, Model model) {
        model.addAttribute("result", boardService.findPageBoard(pageRequestDTO));
        return "board/board_list";
    }

    @GetMapping("/{boardId}")
    public String board(@PathVariable long boardId, Model model) {
        BoardResponseDTO board = boardService.findBoard(boardId);
        model.addAttribute("board", board);
        return "board/board";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("board", new BoardRequestDTO());
        return "board/add_form";
    }

    @PostMapping("/add")
    public String addBoard(BoardRequestDTO boardRequestDTO,
                           @AuthenticationPrincipal User user,
                           RedirectAttributes redirectAttributes) {
        boardRequestDTO.setUser(user);
        Long saveBoardId = boardService.save(boardRequestDTO);
        redirectAttributes.addAttribute("boardId", saveBoardId);
        redirectAttributes.addAttribute("status", true);
        return "redirect:/board/{boardId}";
    }

    @GetMapping("/{boardId}/edit")
    public String editForm(@PathVariable Long boardId, Model model) {
        BoardResponseDTO board = boardService.findBoard(boardId);
        model.addAttribute("board", board);
        return "board/edit_form";
    }

    @PostMapping("/{boardId}/edit")
    public String edit(@PathVariable Long boardId, BoardRequestDTO boardRequestDTO) {
        boardService.update(boardId, boardRequestDTO);
        return "redirect:/board/{boardId}";
    }

    @PostMapping("/{boardId}/delete")
    public String delete(@PathVariable Long boardId) {
        boardService.delete(boardId);
        return "redirect:/board";
    }
}
