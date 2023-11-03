package com.project.readers.service;

import org.springframework.stereotype.Service;

import com.project.readers.repository.ChatMesgDAO;

import jakarta.annotation.Resource;

@Service
public class ChatMesgService {
	@Resource(name="chatMesgDAO")
	private ChatMesgDAO chatMesgDAO;
	
	
}
