package com.project.readers.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.readers.entity.GalleryDTO;

@Mapper
public interface GalleryDAO {
	List<GalleryDTO> getList(GalleryDTO galleryDTO);
	List<GalleryDTO> searchByTitle(GalleryDTO galleryDTO);
	List<GalleryDTO> searchByAuthor(GalleryDTO galleryDTO);
	GalleryDTO getItem(GalleryDTO galleryDTO);
	void saveGallery(GalleryDTO galleryDTO);
	Integer getGalleryCount();
}
