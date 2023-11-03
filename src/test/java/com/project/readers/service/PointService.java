package com.project.readers.service;

import org.springframework.stereotype.Service;

import com.project.readers.repository.PointDAO;

import jakarta.annotation.Resource;

@Service
public class PointService {
	@Resource(name="pointDAO")
	private PointDAO pointDAO;
}
