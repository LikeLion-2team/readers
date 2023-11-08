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
		return "html/index";
	}
	
	@ResponseBody
	@GetMapping("/chart")
	public Map<String, Object>getVisitorChart(){
		Map<String, Object> resultMap = new HashMap<>();
		Map<String, Integer> totalVisiteCount = mainService.totalVisiteCount();
		Map<String, Integer> dayVisiteCount = mainService.dayVisiteCount();
		Map<String, List<VisitorCountDTO>> weekVisitorCount = mainService.weekVisiteCount();
		//숫자,숫자,리스트를 바로 넘겨줘 맵을 한번 덜 감싸게.
		//service value resulemesg 맵에
		//서비스 단 맵을 단을 컨트롤에 결과 넘겨주면 컨트롤단에
		//
		resultMap.put("totalVisiteCount", totalVisiteCount);
		resultMap.put("dayVisiteCount", dayVisiteCount);
		resultMap.put("weekVisitorCount", weekVisitorCount);
		return resultMap;
	}
}
