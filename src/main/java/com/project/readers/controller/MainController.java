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
		HashMap<String,Integer> totalVisiteCount = mainService.totalVisiteCount();
		HashMap<String,Integer> dayVisiteCount = mainService.dayVisiteCount();
		model.addAttribute("mainList", mainList);
		model.addAttribute("totalVisiteCount", totalVisiteCount);
		model.addAttribute("dayVisiteCount", dayVisiteCount);
		return "index";
	}
}
