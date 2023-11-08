package com.project.readers.service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.project.readers.common.Constant;
import com.project.readers.entity.GalleryDTO;
import com.project.readers.repository.GalleryDAO;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GalleryService {
	
	@Value("${fileUploadPath}")
	String fileUploadPath;

	@Value("${domain}")
	String domain;
	
	private final GalleryDAO galleryDAO;
	
	public List<GalleryDTO> getList(GalleryDTO galleryDTO) {
		List<GalleryDTO> items = galleryDAO.getList(galleryDTO);
		return items;
	}
	
	public GalleryDTO getItem(GalleryDTO galleryDTO) {
		//mapper에서 GalleryDTO.galleryNum 데이터로 select
		GalleryDTO selectedItem = galleryDAO.getItem(galleryDTO);
		return selectedItem;
	}
	
	public HashMap<String, Object> saveGallery(GalleryDTO galleryDTO) {
		HashMap<String, Object> resultMap = new HashMap<>();
		galleryDAO.saveGallery(galleryDTO);
		
		System.out.println("gallery num: " + galleryDTO.getGalleryNum());
		
		Long imgNum = galleryDTO.getGalleryNum();
		
		resultMap.put("resultMsg", Constant.SUCCESS_MESG);
		resultMap.put("galleryNum", imgNum);
		return resultMap;
	}

	public HashMap<String, Object> saveFile(MultipartFile file, String fileName) {
		HashMap<String, Object> resultMap = new HashMap<>();
		
		try {
			Path uploadPath = Paths.get(fileUploadPath);
			Path filePath = uploadPath.resolve(fileName);
			InputStream inputStream = file.getInputStream();
			Files.copy(inputStream, filePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resultMap.put("resultMsg", Constant.SUCCESS_MESG);
		resultMap.put("fileName", fileName);
		resultMap.put("imageUrl", domain + "/" + fileUploadPath + "/" + fileName);
		return resultMap;
	}

	public int getGalleryCount() {
		return galleryDAO.getGalleryCount();
	}
}
