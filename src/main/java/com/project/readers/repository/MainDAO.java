package com.project.readers.repository;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.project.readers.entity.BoardDTO;
import com.project.readers.entity.GalleryDTO;
import com.project.readers.entity.VisitorCountDTO;
import com.project.readers.entity.VisitorDTO;

@Mapper
public interface MainDAO {

	List<BoardDTO> getHotBoard(Integer mainViewImg); //5개

	List<GalleryDTO> getHotGallery(Integer mainViewImg); //5개

	Integer checkGuest(String guestIp);
	
	void insertVisitor(String guestIp);

	void countPlusVisist(String guestIp);

	VisitorDTO getLastVisitTime(String guestIp);

	Integer getTotalVisiteCount();

	Integer getDayVisiteCount();

	List<VisitorCountDTO> getWeekVisiteCount(Date sixDayAgoDay);



}
