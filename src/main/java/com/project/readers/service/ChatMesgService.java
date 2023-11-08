package com.project.readers.service;

import com.project.readers.entity.ChatMesgDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.readers.repository.ChatMesgDAO;

import jakarta.annotation.Resource;

@Service
public class ChatMesgService {
	@Autowired
	private ChatMesgDAO chatMesgDAO;

	public void insertChatMesg(ChatMesgDTO chatMesgDTO) {

		chatMesgDAO.insertChatMesg(chatMesgDTO);

	}
}
