package com.project.readers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.readers.repository.BoardDAO;

import jakarta.annotation.Resource;

@Service
public class BoardService {
	@Autowired
	private BoardDAO boardDAO;
}
