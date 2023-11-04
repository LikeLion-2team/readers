package com.project.readers.service;

import org.springframework.stereotype.Service;

import com.project.readers.repository.BoardDAO;

import jakarta.annotation.Resource;

@Service
public class BoardService {
	@Resource(name="boardDAO")
	private BoardDAO boardDAO;
}
