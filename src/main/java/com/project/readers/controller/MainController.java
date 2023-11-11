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

	@GetMapping("/") // 모델 객체
	public String viewMain(Model model) {
		Map<String, List<?>> mainList = mainService.viewMain();
		Integer totalVisiteCount = mainService.totalVisiteCount();
		Integer dayVisiteCount = mainService.dayVisiteCount();
		List<VisitorCountDTO> weekVisitorCount = mainService.weekVisiteCount();
		model.addAttribute("mainList", mainList);
		model.addAttribute("totalCount", totalVisiteCount);
		model.addAttribute("dayCount", dayVisiteCount);
		model.addAttribute("weekCount", weekVisitorCount);
		return "./html/ui-card"; // mainpage
	}

}
