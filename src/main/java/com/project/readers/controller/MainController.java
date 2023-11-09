package com.project.readers.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.project.readers.entity.VisitorCountDTO;
import com.project.readers.service.MainService;

@Controller
public class MainController {
	@Autowired
	private MainService mainService;
	
	@GetMapping("/") //모델 객체
	public String viewMain(Model model) {
		Map<String, List<?>> mainList = mainService.viewMain();
		model.addAttribute("mainList", mainList);
		return "./html/ui-card"; // mainpage
	}
	
	@ResponseBody
	@GetMapping("/chart")
	public Map<String, Object>getVisitorChart(){
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Integer> totalVisiteCount = mainService.totalVisiteCount();
		Map<String, Integer> dayVisiteCount = mainService.dayVisiteCount();
		Map<String, List<VisitorCountDTO>> weekVisitorCount = mainService.weekVisiteCount();
		resultMap.put("totalVisiteCount", totalVisiteCount);
		resultMap.put("dayVisiteCount", dayVisiteCount);
		resultMap.put("weekVisitorCount", weekVisitorCount);
		return resultMap;
	}
}
