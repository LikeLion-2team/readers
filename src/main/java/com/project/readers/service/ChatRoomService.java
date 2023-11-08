package com.project.readers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.readers.repository.ChatRoomDAO;

import jakarta.annotation.Resource;

@Service
public class ChatRoomService {
	@Autowired
	private ChatRoomDAO chatRoomDAO;
}
