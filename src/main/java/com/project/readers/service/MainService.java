package com.project.readers.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.readers.common.Constant;
import com.project.readers.entity.BoardDTO;
import com.project.readers.entity.GalleryDTO;
import com.project.readers.repository.MainDAO;

@Service
@Transactional
public class MainService {
	@Autowired
	private MainDAO mainDAO;

	public HashMap<String, List<?>> viewMain() {
		List<GalleryDTO> getGallery = getHotGallery(Constant.MAIN_VIEW_IMG);
		List<BoardDTO> getBoard = getHotBoard(Constant.MAIN_VIEW_IMG);
		HashMap<String, List<?>> viewMain= handleViewMain(getGallery, getBoard);
		return viewMain;

	}

	private HashMap<String, List<?>> handleViewMain(List<GalleryDTO> getGallery, List<BoardDTO> getBoard) {
		HashMap<String, List<?>> resultMap = new HashMap<>();
		String resultMesg = Constant.FALSE_MESG;
		if (!getGallery.isEmpty()&&!getBoard.isEmpty()) {
			resultMesg=Constant.SUCCESS_MESG;
			resultMap.put(resultMesg, Collections.emptyList());
			resultMap.put("galleryList", getGallery);
			resultMap.put("boardList", getBoard);
		} else {
			resultMap.put(resultMesg, Collections.emptyList());
		}
		return resultMap;
	}

	public List<BoardDTO> getHotBoard(Integer mainViewImg) {
		List<BoardDTO> getBoard = mainDAO.getHotBoard(mainViewImg);
		return getBoard;
	}

	public List<GalleryDTO> getHotGallery(Integer mainViewImg) {
		List<GalleryDTO> getGallery = mainDAO.getHotGallery(mainViewImg);
		return getGallery;
	}

}
