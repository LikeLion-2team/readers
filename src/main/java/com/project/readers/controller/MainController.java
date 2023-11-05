package com.project.readers.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.readers.service.MainService;

@Controller
public class MainController {
	@Autowired
	private MainService mainService;
	
	@GetMapping("/")
	public String viewMain(Model model) {
		HashMap<String, List<?>>mainList= mainService.viewMain();
		model.addAttribute("mainList", mainList);
		return "index";
	}
}
