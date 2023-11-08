package com.project.readers.controller;

import com.project.readers.common.Pager;
import com.project.readers.config.SessionConfig;
import com.project.readers.entity.BoardDTO;
import com.project.readers.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor    // 의존성주입
@Log4j2
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/write")
    public String writeBoard() {
        log.info("writeGet....");
        return "/html/board/board_write";
    }


    @PostMapping("/save")
    @ResponseBody
    public HashMap<String,Object> saveBoard(@Valid @RequestBody BoardDTO boardDTO) {

        log.info("writePost....");
        boardDTO.setCreator(SessionConfig.getSessionDTO().getId());

        HashMap<String,Object> map = new HashMap<String,Object>();
        boardService.createBoard(boardDTO);

        map.put("msg","save success");
        return map;
    }

    @GetMapping("/list/{pg}")
    public String boardList(Model model, BoardDTO boardDTO, @PathVariable("pg")int pg) {
        String page = Pager.makePage(10, boardService.getBoardCount(boardDTO), pg);
        boardDTO.setPg(pg);
        List<BoardDTO> boardList = boardService.getBoardList(boardDTO);
        model.addAttribute("boardList", boardList);
        model.addAttribute("page", page);
        return "/html/board/board_list";
    }

    @GetMapping("/view/{boardNum}")
    public String boardDetail(@PathVariable("boardNum") Integer boardNum, Model model) {
        BoardDTO boardDTO = boardService.getBoardOne(boardNum);
        model.addAttribute("boardDetail", boardDTO);
        return "/html/board/board_detail";
    }

    @GetMapping("/edit/{boardNum}")
    public String updateBoard(@PathVariable("boardNum") Integer boardNum, Model model) {
        BoardDTO boardDTO = boardService.getBoardOne(boardNum);
        model.addAttribute("board", boardDTO);
        return "/html/board/board_edit";
    }

    @PutMapping("/modify/{boardNum}")
    @ResponseBody
    public HashMap<String,Object> updateBoard(@PathVariable("boardNum") Integer boardNum, @RequestBody BoardDTO boardDTO) {
        boardDTO.setBoardNum(boardNum);
        boardService.updateBoard(boardDTO);

        HashMap<String,Object> map = new HashMap<String,Object>();
        map.put("msg", "modify success");
        return map;
    }

    @DeleteMapping("/delete/{boardNum}")
    @ResponseBody
    public HashMap<String,Object> deleteBoard(@PathVariable("boardNum") Integer boardNum) {
        boardService.deleteBoard(boardNum);
        HashMap<String, Object> map = new HashMap<String,Object>();
        map.put("msg", "delete success");
        return map;
    }
}
