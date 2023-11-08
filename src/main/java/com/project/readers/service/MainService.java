package com.project.readers.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.readers.common.Constant;
import com.project.readers.config.IPconfig;
import com.project.readers.config.SessionConfig;
import com.project.readers.entity.BoardDTO;
import com.project.readers.entity.GalleryDTO;
import com.project.readers.entity.VisitorCountDTO;
import com.project.readers.entity.VisitorDTO;
import com.project.readers.repository.MainDAO;

import oracle.sql.DATE;

@Service
@Transactional
public class MainService {
	@Autowired
	private MainDAO mainDAO;

	// 메인 화면
	public Map<String, List<?>> viewMain() {
//		checkVisistCount();
		List<GalleryDTO> getGallery = getHotGallery(Constant.MAIN_VIEW_IMG);
		System.err.println(getGallery.size());
		List<BoardDTO> getBoard = getHotBoard(Constant.MAIN_VIEW_IMG);
		Map<String, List<?>> viewMain = handleViewMain(getGallery, getBoard);
		return viewMain;
	}

	// DB 결과 값 확인
	private Map<String, List<?>> handleViewMain(List<GalleryDTO> getGallery, List<BoardDTO> getBoard) {
		Map<String, List<?>> resultMap = new HashMap<>();
		String id = SessionConfig.getSessionDTO().getId();
		BoardDTO boardDTO = new BoardDTO();
		boardDTO.setCreator(id);
		String resultMesg = Constant.FALSE_MESG;
		if (!getGallery.isEmpty() && !getBoard.isEmpty()) {
			resultMesg = Constant.SUCCESS_MESG;
			resultMap.put(resultMesg, Collections.emptyList());
			resultMap.put("galleryList", getGallery);
			resultMap.put("boardList", getBoard);
		} else {
			resultMap.put(resultMesg, Collections.emptyList());
		}
		return resultMap;
	}

	// 방문자 확인
	private void checkVisistCount() {
		String guestIp = IPconfig.getIp(SessionConfig.getSession());
		String newUser = mainDAO.checkGuest(guestIp); // 첫 방문자인지 아니면 처음 방문자가 아닌지 확인
		if (newUser != Constant.SEARCH_SAME_VALUE.toString())
			mainDAO.insertVisitor(guestIp); // 방문자 추가
		else
			updateGuest(guestIp); // 방문자 업데이트
	}

	// 재방문자 업데이트 하루에 한번만 하도록
	private void updateGuest(String guestIp) {
		VisitorDTO lastVisiteDTO = mainDAO.getLastVisitTime(guestIp);
		VisitorDTO visitor = handleVsitor(guestIp);
		boolean checkUpdate = compareUpdateGuest(lastVisiteDTO, visitor);
		if (checkUpdate) {
			mainDAO.countPlusVisist(guestIp);
		}
	}

	// VsitorDTO 핸들링.
	private VisitorDTO handleVsitor(String guestIp) {
		VisitorDTO visitor = new VisitorDTO();
		DATE nowTime = new DATE(new Date(System.currentTimeMillis()));
		// 기억하자 Oracle 날짜 변함함수
		visitor.setIp(guestIp);
		visitor.setVisiteTm(nowTime);
		return visitor;
	}

	// 방문 시간 비교.
	// Oralce: DATE 포맷 = HH.MI.SSXFF AM 타임 포맷 오류 날시 DTO에서 변경
	// Oracle.Date 가져왔고
	private boolean compareUpdateGuest(VisitorDTO lastVisiteDTO, VisitorDTO visitor) {
		if (lastVisiteDTO.getVisiteTm() == visitor.getVisiteTm())
			return true;
		return false;
	}

	// 보드 게시판 불러오기
	public List<BoardDTO> getHotBoard(Integer mainViewImg) {
		List<BoardDTO> getBoard = mainDAO.getHotBoard(mainViewImg);
		return getBoard;
	}

	// 갤러리 게시판 불러오기
	public List<GalleryDTO> getHotGallery(Integer mainViewImg) {
		List<GalleryDTO> getGallery = mainDAO.getHotGallery(mainViewImg);
		return getGallery;
	}

	// 전체 방문자 수 가져오기
	public Map<String, Integer> totalVisiteCount() {
		Map<String, Integer> resultMap = new HashMap<>();
		Integer totalVisitor = mainDAO.getTotalVisiteCount();
		resultMap = handleVisisteCount(totalVisitor);
		return resultMap;
	}

	// 하루 방문자 수 가져오기
	public Map<String, Integer> dayVisiteCount() {
		Map<String, Integer> resultMap = new HashMap<>();
		LocalDateTime nowTime = LocalDateTime.now();
		String nowDay = dateFormat(nowTime);
		Integer totalVisitor = mainDAO.getDayVisiteCount(nowDay);
		resultMap = handleVisisteCount(totalVisitor);
		return resultMap;
	}

	// 7일간 방문자 수 가져오기
	public Map<String, List<VisitorCountDTO>> weekVisiteCount() {
		Map<String, List<VisitorCountDTO>> resultMap = new HashMap<>();
		Map<String, String>insertValue = inserWeekVisiteValue();
		List<VisitorCountDTO> totalVisitor = mainDAO.getWeekVisiteCount(insertValue);
		resultMap = handleWeekVisisteCount(totalVisitor);
		return resultMap;
	}
	
	//급하면 쓰는구나;;
	private Map<String, String> inserWeekVisiteValue() {
		Map<String, String> insertMap = new HashMap<>();
		LocalDateTime nowTime = LocalDateTime.now();
		LocalDateTime sixDaysAgo = LocalDateTime.now().minusDays(6);
		String nowDay = dateFormat(nowTime);
		String sixDayAgoDay = dateFormat(sixDaysAgo);
		insertMap.put("today", nowDay);
		insertMap.put("sixDayAgoDay", sixDayAgoDay);
		return insertMap;
	}

	// DATE 형식 변환 mapper 에러 주의
	private String dateFormat(LocalDateTime Time) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String dateForm = Time.format(formatter);
		return dateForm;
	}

	// 방문자 수 DB결과 값 따른 리턴
	private Map<String, Integer> handleVisisteCount(Integer totalVisitor) {
		Map<String, Integer> resultMap = new HashMap<>();
		String resultMesg = Constant.FALSE_MESG;
		if (totalVisitor > Constant.MIN_VISITOR)
			resultMap.put(Constant.SUCCESS_MESG, totalVisitor);
		else
			resultMap.put(resultMesg, Constant.MIN_VISITOR);
		return resultMap;
	}

	// 주간 방문자 DB결과 값 따른 리턴
	private Map<String, List<VisitorCountDTO>> handleWeekVisisteCount(List<VisitorCountDTO> totalVisitor) {
		Map<String, List<VisitorCountDTO>> resultMap = new HashMap<>();
		String resultMesg = Constant.FALSE_MESG;
		if (!totalVisitor.isEmpty())
			resultMap.put(Constant.SUCCESS_MESG, totalVisitor);
		else
			resultMap.put(resultMesg, Collections.emptyList());
		return resultMap;
	}

}
