package com.project.readers.service;

import org.springframework.stereotype.Service;

import com.project.readers.repository.GalleryDAO;

import jakarta.annotation.Resource;

@Service
public class GalleryService {
	@Resource(name="galleryDAO")
	private GalleryDAO galleryDAO;
}
