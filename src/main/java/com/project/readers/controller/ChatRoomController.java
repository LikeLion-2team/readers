package com.project.readers.controller;

import com.project.readers.entity.UserSessionDTO;
import com.project.readers.service.ChatRoomService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chat")
public class ChatRoomController {
    @Autowired
    private final ChatRoomService chatRoomService;

    @GetMapping()
    public String enterChatRoom() {

        return chatRoomService.enterChatRoom();
    }

    @GetMapping("/room")
    public String showChat(HttpServletRequest request) {

        HttpSession session = request.getSession();
        UserSessionDTO sessionDTO = new UserSessionDTO();
        sessionDTO.setId("admin");
        sessionDTO.setRoleNum(3);
        session.setAttribute("chatRoomDTO", sessionDTO);

        return "chat"; // main 페이지로 수정
    }

}
