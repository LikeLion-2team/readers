package com.project.readers.service;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import oracle.sql.DATE;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class MainService {

	private final MainDAO mainDAO;

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
			log.error("handleViewMain에서 알 수 없는 값 발견 getGallery::" + getGallery + ", getBoard::" + getBoard);
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
		// 0일 때
		if (visitor == Constant.MIN_VISITOR)
			visitor = 1;
		else if (visitor > Constant.MIN_VISITOR)
			return visitor;
		else
			log.error("checkValueVisitor 알수 없는 값 발견 visitor:: " + visitor);
		throw new IllegalArgumentException(Constant.UNKNOWN_VALUE);

	}

	// 7일간 각 일 방문자 수 가져오기
	public List<VisitorCountDTO> weekVisiteCount() {
		List<VisitorCountDTO> totalVisitor = mainDAO.getWeekVisiteCount();
		List<VisitorCountDTO> checkValue = checkValueVisitorTypeList(totalVisitor);
		return checkValue;
	}

	// 7일간 각 일일 방문자 수 DB값 체크
	private List<VisitorCountDTO> checkValueVisitorTypeList(List<VisitorCountDTO> totalVisitor) {
		// 사이즈가 0 이거나 null일 때
		if (totalVisitor.size() == Constant.MIN_VISITOR || totalVisitor == null)
			totalVisitor = Collections.emptyList();
		else if (totalVisitor.size() > Constant.MIN_VISITOR)
			return totalVisitor;
		else
			log.error("checkValueVisitorTypeList 알수 없는 값 발견 totalVisitor:: " + totalVisitor);
		throw new IllegalArgumentException(Constant.UNKNOWN_VALUE);
	}

	// 방문자 권한 체크 부분 입니다.
	// 방문자 확인 기능입니다.
	private void checkVisistCount() {
		String guestIp = IPconfig.getIp(SessionConfig.getSession());
		Integer newUser = mainDAO.checkGuest(guestIp); // 첫 방문자인지 아니면 처음 방문자가 아닌지 확인
		if (newUser == Constant.MIN_VISITOR) // 첫 방문자 0 재방문자 1이상 0일때 인서트합니다.
			mainDAO.insertVisitor(guestIp);
		else
			updateGuest(guestIp);
	}

	// 재방문자 업데이트 하루에 한번만 하도록 제한하는 기능입니다.
	private void updateGuest(String guestIp) {
		VisitorDTO lastVisiteDTO = mainDAO.getLastVisitTime(guestIp);
		VisitorDTO visitor = handleVsitor(guestIp);
		boolean checkUpdate = compareUpdateGuest(lastVisiteDTO, visitor); //언제 재방문했는지 확인하는 메서드입니다.
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

	// Oralce: DATE 포맷 = HH.MI.SSXFF AM 타임 포맷 오류 날시 DTO에서 변경하는 편이 좋습니다.
	// 지금 방문한 날과 최근 방문한 날과 비교하는 메서드 입니다.
	private boolean compareUpdateGuest(VisitorDTO lastVisiteDTO, VisitorDTO visitor) {
		LocalDateTime date = converterToTime(lastVisiteDTO.getVisiteTm());
		LocalDateTime compareDate = converterToTime(visitor.getVisiteTm());
		Duration duration = Duration.between(date, compareDate); // duration:: 1보다 같거나 커야 증가합니다.
		if (duration.toDays() >= 1)
			return true;

		return false;
	}

	// ORACLE DATE 타입을 java date 타입으로 변환해주는 기능입니다.
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
