package com.project.readers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.readers.repository.PointDAO;

import jakarta.annotation.Resource;

@Service
public class PointService {
	@Autowired
	private PointDAO pointDAO;
}
