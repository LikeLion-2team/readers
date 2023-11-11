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

	// 게시판과 갤러리 DB 결과 값 확인
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

	// 전체 방문자 수 가져오기
	public Integer totalVisiteCount() {
		Integer totalVisitor = mainDAO.getTotalVisiteCount();
		Integer checkValue = checkValueVisitor(totalVisitor);
		return checkValue;
	}

	// 하루 방문자 수 가져오기
	public Integer dayVisiteCount() {
		Integer totalDayVisitor = mainDAO.getDayVisiteCount();
		Integer checkValue = checkValueVisitor(totalDayVisitor);
		return checkValue;
	}

	// 전체 방문자와 하루 방문자 값 확인해서 보내주기.
	private Integer checkValueVisitor(Integer visitor) {
		//0일 때
		if (visitor == Constant.MIN_VISITOR)
			visitor = 1;
		else if (visitor > Constant.MIN_VISITOR)
			return visitor;
		else
			// 여기다가 로그도 남기는 것이 더 좋습니다.
			throw new IllegalArgumentException(Constant.UNKNOWN_VALUE);
		return null;

	}

	// 7일간 각 일 방문자 수 가져오기
	public List<VisitorCountDTO> weekVisiteCount() {
		List<VisitorCountDTO> totalVisitor = mainDAO.getWeekVisiteCount();
		List<VisitorCountDTO> checkValue = checkValueVisitorTypeList(totalVisitor);
		return checkValue;
	}

	// 7일간 각 일일 방문자 수 DB값 체크
	private List<VisitorCountDTO> checkValueVisitorTypeList(List<VisitorCountDTO> totalVisitor) {
		//사이즈가 0 이거나 null일 때
		if (totalVisitor.size() == Constant.MIN_VISITOR || totalVisitor == null)
			totalVisitor = Collections.emptyList();
		else if (totalVisitor.size() > Constant.MIN_VISITOR)
			return totalVisitor;
		else
			//여기다가 로그도 남기는 편이 좋습니다.
			throw new IllegalArgumentException(Constant.UNKNOWN_VALUE);
		return null;

	}

	//방문자 권한 체크 부분 입니다.

	// 방문자 확인
	private void checkVisistCount() {
		String guestIp = IPconfig.getIp(SessionConfig.getSession());
		Integer newUser = mainDAO.checkGuest(guestIp); // 첫 방문자인지 아니면 처음 방문자가 아닌지 확인
		if (newUser != Constant.MIN_VISITOR)
			mainDAO.insertVisitor(guestIp); // 0이 아닐 때 방문자 추가
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
		System.err.println(date);
		System.err.println(compareDate);
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


}
