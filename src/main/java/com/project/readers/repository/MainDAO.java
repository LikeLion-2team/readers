package com.project.readers.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.readers.entity.BoardDTO;
import com.project.readers.entity.GalleryDTO;
import com.project.readers.entity.VisitorCountDTO;
import com.project.readers.entity.VisitorDTO;

@Mapper
public interface MainDAO {

	List<BoardDTO> getHotBoard(Integer mainViewImg);

	List<GalleryDTO> getHotGallery(Integer mainViewImg);

	int checkGuest(String guestIp);
	
	void insertVisitor(String guestIp);

	void countPlusVisist(String guestIp);

	VisitorDTO getLastVisitTime(String guestIp);

	Integer getTotalVisiteCount();

	Integer getDayVisiteCount(String nowTime);

	VisitorCountDTO getWeekVisiteCount(String nowTime);


}
