package com.project.readers.service;

import org.springframework.stereotype.Service;

import com.project.readers.repository.ReplyDAO;

import jakarta.annotation.Resource;

@Service
public class ReplyService {
	@Resource(name="replyDAO")
	private ReplyDAO replyDAO;
}
