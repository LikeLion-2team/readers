package com.project.readers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.readers.repository.GalleryDAO;

import jakarta.annotation.Resource;

@Service
public class GalleryService {
	@Autowired
	private GalleryDAO galleryDAO;
}
