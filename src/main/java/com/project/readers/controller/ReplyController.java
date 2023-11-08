package com.project.readers.controller;

import com.project.readers.config.SessionConfig;
import com.project.readers.entity.ReplyDTO;
import com.project.readers.service.ReplyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/replies")
@RequiredArgsConstructor    // 의존성주입
@Log4j2
public class ReplyController {

    private final ReplyService replyService;

    @GetMapping("/write")
    public String writeReply() {
        log.info("writeReply...");
        return "/html/reply/reply_write";
    }

    @PostMapping(value = "/save")
    @ResponseBody
    public HashMap<String,Object> saveReply(@Valid @RequestBody ReplyDTO replyDTO){

        log.info("writeReply....");
        replyDTO.setCreator(SessionConfig.getSessionDTO().getId());

        HashMap<String,Object> map = new HashMap<String,Object>();
        replyService.createReply(replyDTO);

        map.put("result", "success");
        return map;
    }

    @GetMapping("/list/{boardNum}")
    public String getReplyList(Model model, @PathVariable("boardNum") Integer boardNum) {
        List<ReplyDTO> replyList = replyService.getReplyList(boardNum);
        model.addAttribute("replyList", replyList);
        return "/html/reply/reply_list";
    }

    @GetMapping("/view/{rplNum}")
    public String getReplyOne(@PathVariable("rplNum") Integer rplNum, Model model) {
        ReplyDTO replyDTO = replyService.getReplyOne(rplNum);
        model.addAttribute("replyDetail", replyDTO);
        return "/html/reply/reply_detail";
    }

    @GetMapping("/edit/{rplNum}")
    public String updateReply(@PathVariable("rplNum") Integer rplNum, Model model) {
        ReplyDTO replyDTO = replyService.getReplyOne(rplNum);
        model.addAttribute("reply", replyDTO);
        return "/html/reply/reply_edit";
    }

    @PutMapping("/modify/{rplNum}")
    @ResponseBody
    public HashMap<String,Object> updateReply(@PathVariable("rplNum")Integer rplNum, @RequestBody ReplyDTO replyDTO) {
        replyDTO.setRplNum(rplNum);
        replyService.updateReply(replyDTO);

        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("msg", "modify success");
        return map;
    }

    @DeleteMapping("/delete/{rplNum}")
    @ResponseBody
    public HashMap<String, Object> deleteReply(@PathVariable("rplNum") Integer rplNum){
        replyService.deleteReply(rplNum);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("msg", "delete success");
        return map;
    }
}
