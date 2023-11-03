package com.project.readers.service;

import org.springframework.stereotype.Service;

import com.project.readers.repository.UserDAO;

import jakarta.annotation.Resource;

@Service
public class UserService {
	@Resource(name="userDAO")
	private UserDAO userDAO;
}
