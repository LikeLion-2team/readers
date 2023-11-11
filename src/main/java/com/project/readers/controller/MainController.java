package com.project.readers.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.project.readers.entity.VisitorCountDTO;
import com.project.readers.service.BoardService;
import com.project.readers.service.MainService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MainController {

	private final MainService mainService;

	@GetMapping("/")
	public String viewMain(Model model) {
		//게시판 갤러리 가져오는 메서드
		Map<String, List<?>> mainList = mainService.viewMain();
		//차트에 필요한 메서드
		Integer totalVisiteCount = mainService.totalVisiteCount();
		Integer dayVisiteCount = mainService.dayVisiteCount();
		List<VisitorCountDTO> weekVisitorCount = mainService.weekVisiteCount();
		//
		model.addAttribute("mainList", mainList);
		model.addAttribute("totalCount", totalVisiteCount);
		model.addAttribute("dayCount", dayVisiteCount);
		model.addAttribute("weekCount", weekVisitorCount);
		return "./html/ui-card"; // mainpage
	}

}
