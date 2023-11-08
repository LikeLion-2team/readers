package com.project.readers.controller;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.project.readers.common.Pager;
import com.project.readers.config.SessionConfig;
import com.project.readers.entity.GalleryDTO;
import com.project.readers.service.GalleryService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/gallery")
public class GalleryController {

	private final GalleryService galleryService;

	@Value("${fileUploadPath}")
	String fileUploadPath;

	@Value("${domain}")
	String domain;

	@GetMapping("/list/{pg}")
	public String showList(@PathVariable int pg, Model model) {
		GalleryDTO galleryDTO = new GalleryDTO();
		galleryDTO.setPg(pg);
		List<GalleryDTO> bookList = galleryService.getList(galleryDTO);
		String page = Pager.makePage(10, galleryService.getGalleryCount(), pg);
		
		model.addAttribute("bookList", bookList);
		model.addAttribute("page", page);
		return "/gallery/gallery_list";
	}

	@GetMapping("/book/{id}")
	public String showBook(@PathVariable Long id, Model model) {
		GalleryDTO galleryDTO = new GalleryDTO();
		galleryDTO.setGalleryNum(id);

		GalleryDTO bookInfo = galleryService.getItem(galleryDTO);
		model.addAttribute("bookInfo", bookInfo);
		return "/gallery/gallery_view";
	}

	@GetMapping("/write")
	public String showWrite() {
		return "/gallery/gallery_write";
	}

	@ResponseBody
	@PostMapping("/save")
	public HashMap<String, Object> save(GalleryDTO galleryDTO) {
		HashMap<String, Object> resultMap = new HashMap<>();
		String id = SessionConfig.getSessionDTO().getId();
		galleryDTO.setCreator(id);
		galleryDTO.setCatNum(1);

		HashMap<String, Object> saveGalleryMap = galleryService.saveGallery(galleryDTO);
		
		resultMap.put("resultMsg", saveGalleryMap.get("resultMsg"));
		resultMap.put("galleryNum", saveGalleryMap.get("galleryNum"));
		
		return resultMap;
	}
	
	@ResponseBody
	@PostMapping("/imgUpload")
	public HashMap<String, Object> saveFile(MultipartFile file, String fileName) {
		HashMap<String, Object> resultMap = new HashMap<>();
		HashMap<String, Object> saveFileMap = galleryService.saveFile(file, fileName);
		System.out.println("file name: " + fileName);
		resultMap.put("resultMsg", saveFileMap.get("resultMsg"));
		resultMap.put("fileName", saveFileMap.get("fileName"));
		resultMap.put("imageUrl", saveFileMap.get("imageUrl"));
		
		return resultMap;
	}	
}