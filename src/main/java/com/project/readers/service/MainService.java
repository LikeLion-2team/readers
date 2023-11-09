package com.project.readers.service;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		checkVisistCount();
		List<GalleryDTO> getGallery = getHotGallery(Constant.MAIN_VIEW_IMG);
		List<BoardDTO> getBoard = getHotBoard(Constant.MAIN_VIEW_IMG);
		Map<String, List<?>> viewMain = handleViewMain(getGallery, getBoard);
		return viewMain;
	}

	// DB 결과 값 확인
	private Map<String, List<?>> handleViewMain(List<GalleryDTO> getGallery, List<BoardDTO> getBoard) {
		Map<String, List<?>> resultMap = new HashMap<>();
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
		Integer newUser = mainDAO.checkGuest(guestIp); // 첫 방문자인지 아니면 처음 방문자가 아닌지 확인
		if (newUser != Constant.MIN_VISITOR)
			mainDAO.insertVisitor(guestIp); // 방문자 추가
		else
			updateGuest(guestIp);
//		else
//			throw new IllegalArgumentException(Constant.UNKNOWN_ACCESS);
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
		// 전자 Oracle DATE 후자 java Date
		visitor.setIp(guestIp);
		visitor.setVisiteTm(nowTime);
		return visitor;
	}

	// Oralce: DATE 포맷 = HH.MI.SSXFF AM 타임 포맷 오류 날시 DTO에서 변경
	private boolean compareUpdateGuest(VisitorDTO lastVisiteDTO, VisitorDTO visitor) {
		LocalDateTime date = converterToTime(lastVisiteDTO.getVisiteTm());
		LocalDateTime compareDate = converterToTime(visitor.getVisiteTm());
		Duration duration = Duration.between(date, compareDate);
		if (duration.toDays() <= 1)
			return true;

		return false;
	}

	public LocalDateTime converterToTime(DATE date) {
		if (date == null) {
			date = new DATE(new Timestamp(System.currentTimeMillis()));
		}
		LocalDateTime localDateTime = LocalDateTime.now();
		try {
			localDateTime = date.toLocalDateTime();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return localDateTime;
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
		Integer totalVisitor = mainDAO.getDayVisiteCount();
		resultMap = handleVisisteCount(totalVisitor);
		return resultMap;
	}

	// 7일간 방문자 수 가져오기
	public Map<String, List<VisitorCountDTO>> weekVisiteCount() {
		Map<String, List<VisitorCountDTO>> resultMap = new HashMap<>();
		Date sixDayAgoDay = inserWeekVisiteValue();
		List<VisitorCountDTO> totalVisitor = mainDAO.getWeekVisiteCount(sixDayAgoDay);
		System.err.println("totalVisitor::" + totalVisitor);
		resultMap = handleWeekVisisteCount(totalVisitor);
		return resultMap;
	}

	// 급하면 쓰는구나;;
	private Date inserWeekVisiteValue() {
		LocalDateTime sixDaysAgo = LocalDateTime.now().minusDays(6);
		System.err.println("sixDaysAgo ::" + sixDaysAgo);
		Date sixDayAgoDay = dateFormat(sixDaysAgo);
		return sixDayAgoDay;
	}

	// DATE 형식 변환 mapper 에러 주의
	private Date dateFormat(LocalDateTime Time) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String dateForm = Time.format(formatter);
		LocalDate localDate = LocalDate.parse(dateForm, formatter);
		Date inputValue = Date.valueOf(localDate);
		System.err.println("dateFormat::"+inputValue);
		return inputValue;
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
