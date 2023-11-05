package com.project.readers.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.readers.entity.TagDTO;
import com.project.readers.repository.AdminDAO;

import jakarta.annotation.Resource;

@Service
public class AdminService {
	@Resource(name = "AdminDAO")
	private AdminDAO adminDAO;

	public List<TagDTO> getFooter() {
		String footer = "footer";
		List<TagDTO> footInfo = adminDAO.getFooter(footer);
		if (footInfo == null)
			return Collections.emptyList();

		return footInfo;
	}

}
