package com.project.readers.controller;

import com.project.readers.service.ChatRoomService;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @GetMapping("/chat")

    public String enterChatRoom(Model model) {
    	String accessId = chatRoomService.accessId();
    	model.addAttribute("accessId",accessId);
    	return chatRoomService.enterChatRoom();
    }


}