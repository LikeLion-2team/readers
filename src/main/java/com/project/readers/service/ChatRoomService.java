package com.project.readers.service;

import org.springframework.stereotype.Service;

import com.project.readers.repository.ChatRoomDAO;

import jakarta.annotation.Resource;

@Service
public class ChatRoomService {
	@Resource(name="chatRoomDAO")
	private ChatRoomDAO chatRoomDAO;
}
