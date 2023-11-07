package com.project.readers.service;

import org.springframework.stereotype.Service;

import com.project.readers.repository.AdminDAO;

import jakarta.annotation.Resource;

@Service
public class AdminService {
	@Resource(name = "AdminDAO")
	private AdminDAO adminDAO;


}
