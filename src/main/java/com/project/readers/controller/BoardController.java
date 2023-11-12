package com.project.readers.controller;

import com.project.readers.common.Pager;
import com.project.readers.entity.BoardDTO;
import com.project.readers.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor    // 의존성주입
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/write")
    public String writeBoard() {
        return "/board/board_write";
    }


    @PostMapping("/save")
    @ResponseBody
    public HashMap<String,Object> saveBoard(@Valid @RequestBody BoardDTO boardDTO) {

        HashMap<String,Object> map = new HashMap<String,Object>();
        try {
            boardService.createBoard(boardDTO);
            map.put("msg","save success");
        } catch (SecurityException e) {
            map.put("msg", e.getMessage());
        }
        return map;

    }

    @GetMapping("/list/{pg}")
    public String boardList(Model model, BoardDTO boardDTO, @PathVariable("pg")int pg) {
        String page = Pager.makePage(10, boardService.getBoardCount(boardDTO), pg);
        boardDTO.setPg(pg);
        List<BoardDTO> boardList = boardService.getBoardList(boardDTO);
        model.addAttribute("boardList", boardList);
        model.addAttribute("page", page);
        return "/board/board_list";
    }

    @GetMapping("/update/{boardNum}")
    public String updateBoard(@PathVariable("boardNum") Integer boardNum, Model model) {
        BoardDTO boardDTO = boardService.getBoardOne(boardNum);
        model.addAttribute("board", boardDTO);
        return "/board/board_update";
    }

    @PutMapping("/update/{boardNum}")
    @ResponseBody
    public HashMap<String,Object> updateBoard(@PathVariable("boardNum") Integer boardNum, @RequestBody BoardDTO boardDTO) {
        HashMap<String,Object> map = new HashMap<String,Object>();

        try {
            boardDTO.setBoardNum(boardNum);
            boardService.updateBoard(boardDTO);
            map.put("msg", "update success");
        } catch (SecurityException e) {
            map.put("msg", e.getMessage());
        }

        return map;
    }

    @DeleteMapping("/delete/{boardNum}")
    @ResponseBody
    public HashMap<String,Object> deleteBoard(@PathVariable("boardNum") Integer boardNum) {
        HashMap<String, Object> map = new HashMap<String,Object>();
        try {
            boardService.deleteBoard(boardNum);
            map.put("msg", "delete success");
        } catch (SecurityException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }
}
