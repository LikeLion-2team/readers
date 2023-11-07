package com.project.readers.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.readers.entity.VisitorCountDTO;
import com.project.readers.service.MainService;

@Controller
public class MainController {
	@Autowired
	private MainService mainService;

	@GetMapping("/")
	public String viewMain(Model model) {
		Map<String, List<?>> mainList = mainService.viewMain();
		Map<String, Integer> totalVisiteCount = mainService.totalVisiteCount();
		Map<String, Integer> dayVisiteCount = mainService.dayVisiteCount();
		Map<String, List<VisitorCountDTO>> weekVisitorCount = mainService.weekVisiteCount();
		model.addAttribute("mainList", mainList);
		model.addAttribute("totalVisiteCount", totalVisiteCount);
		model.addAttribute("dayVisiteCount", dayVisiteCount);
		model.addAttribute("weekVisitorCount", weekVisitorCount);
		return "html/index";
	}
}
