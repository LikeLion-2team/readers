package com.project.readers.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.readers.entity.BoardDTO;
import com.project.readers.entity.GalleryDTO;

@Mapper
public interface MainDAO {

	List<BoardDTO> getHotBoard(Integer mainViewImg);

	List<GalleryDTO> getHotGallery(Integer mainViewImg);

}
