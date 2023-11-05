package com.project.readers.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.project.readers.entity.TagDTO;

@Mapper
public interface AdminDAO {

	List<TagDTO> getFooter(String footer);

}
