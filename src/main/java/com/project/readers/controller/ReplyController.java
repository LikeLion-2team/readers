package com.project.readers.controller;

import com.project.readers.entity.ReplyDTO;
import com.project.readers.service.ReplyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/replies")
@RequiredArgsConstructor    // 의존성주입
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("/write")
    public String writeReply() {
        return "/reply/reply_write";
    }

    @PostMapping(value = "/save")
    @ResponseBody
    public HashMap<String, Object> saveReply(@Valid @RequestBody ReplyDTO replyDTO) {

        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            replyService.createReply(replyDTO);
            map.put("msg", "success");
        } catch (SecurityException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }

    @GetMapping("/list/{boardNum}")
    public String getReplyList(Model model, @PathVariable("boardNum") Integer boardNum) {
        List<ReplyDTO> replyList = replyService.getReplyList(boardNum);
        model.addAttribute("replyList", replyList);
        return "/html/reply/reply_list";
    }

    @GetMapping("/update/{rplNum}")
    public String updateReply(@PathVariable("rplNum") Integer rplNum, Model model) {
        ReplyDTO replyDTO = replyService.getReplyOne(rplNum);
        model.addAttribute("reply", replyDTO);
        return "/html/reply/reply_edit";
    }

    @PutMapping("/update/{rplNum}")
    @ResponseBody
    public HashMap<String, Object> updateReply(@PathVariable("rplNum") Integer rplNum, @RequestBody ReplyDTO replyDTO) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            replyService.updateReply(rplNum, replyDTO); // 서비스의 updateReply 메소드에 rplNum 추가
            map.put("msg", "update success");
        } catch (SecurityException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }


    @DeleteMapping("/delete/{rplNum}")
    @ResponseBody
    public HashMap<String, Object> deleteReply(@PathVariable("rplNum") Integer rplNum) {
        HashMap<String, Object> map = new HashMap<String, Object>();
        try {
            replyService.deleteReply(rplNum);
            map.put("msg", "delete success");
        } catch (SecurityException e) {
            map.put("msg", e.getMessage());
        }
        return map;
    }
}