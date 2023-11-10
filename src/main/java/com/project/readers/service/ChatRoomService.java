package com.project.readers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.readers.config.SessionConfig;
import com.project.readers.repository.ChatRoomDAO;

@Service
public class ChatRoomService {
    @Autowired
    private ChatRoomDAO chatRoomDAO;

    public String enterChatRoom() {
        String id = accessId();
        System.err.println("ChatRoomDAO ::" + id);
        if (id != null) {
        	return "chat"; // 채팅방 페이지 (chat.html)
        	//login 잘이동 그러면 html로 가봐야함
        } else {
        	return "login"; // 로그인 페이지로
        }
    }

	public String accessId() {
		String id = SessionConfig.getSessionDTO().getId();
		return id;
	}

}