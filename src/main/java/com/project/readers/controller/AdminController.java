package com.project.readers.controller;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;

import com.project.readers.service.AdminService;

import jakarta.annotation.Resource;

@Controller
@Cacheable("footerCache")
public class AdminController {
	@Resource(name="AdminService")
	private AdminService adminService;
	
}
