package com.project.readers.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.readers.entity.TagDTO;
import com.project.readers.service.AdminService;

import jakarta.annotation.Resource;

@Controller
@Cacheable("footerCache")
public class AdminController {
	@Resource(name="AdminService")
	private AdminService adminService;
	
	@GetMapping("/footer")
	public HashMap<String, List<TagDTO>> viewFooter(Model model) {
		HashMap<String, List<TagDTO>> resultMap = new HashMap<>();
		List<TagDTO> infoFooterTag = adminService.getFooter();
		model.addAttribute("footer",infoFooterTag);
		return resultMap;
	}
}
