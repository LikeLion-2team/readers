package com.project.readers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.readers.repository.ReplyDAO;

import jakarta.annotation.Resource;

@Service
public class ReplyService {
	@Autowired
	private ReplyDAO replyDAO;
}
