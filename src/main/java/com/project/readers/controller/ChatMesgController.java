package com.project.readers.controller;

import com.project.readers.entity.ChatMesgDTO;
import com.project.readers.service.ChatMesgService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
public class ChatMesgController {
    @Autowired
    private final ChatMesgService chatMesgService;

    @MessageMapping("/mesg/{chatRoomId}")
    @SendTo("/topic/chatroom/{chatRoomId}")
    public ChatMesgDTO sendMessage(@DestinationVariable String chatRoomId, ChatMesgDTO chatMesgDTO) throws InterruptedException {
        Thread.sleep(500); // 빼도 됨
        // 채팅 메시지 저장

        chatMesgService.insertChatMesg(chatMesgDTO);

        return chatMesgDTO;
    }


}
