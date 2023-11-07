package com.project.readers.repository;

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

	String checkGuest(String guestIp);
	
	void insertVisitor(String guestIp);

	void countPlusVisist(String guestIp);

	VisitorDTO getLastVisitTime(String guestIp);

	Integer getTotalVisiteCount();

	Integer getDayVisiteCount(String nowTime);

	List<VisitorCountDTO> getWeekVisiteCount(Map<String, String> insertValue);


}
