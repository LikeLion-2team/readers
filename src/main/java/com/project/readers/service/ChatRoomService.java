package com.project.readers.service;

import com.project.readers.config.SessionConfig;
import org.springframework.stereotype.Service;

import com.project.readers.repository.ChatRoomDAO;

import jakarta.annotation.Resource;

@Service
public class ChatRoomService {
	@Resource(name="chatRoomDAO")
	private ChatRoomDAO chatRoomDAO;

	public String enterChatRoom() {
		String id = SessionConfig.getSessionDTO().getId();
		if (id != null) {
			return "redirect:/chat/room";
		} else {
			return "login"; // 로그인 페이지로
		}
	}

}
