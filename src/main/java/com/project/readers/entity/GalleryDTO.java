package com.project.readers.entity;

import com.project.readers.common.BaseDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class GalleryDTO extends BaseDTO {

	// 갤러리 번호 
	private Long galleryNum = 0L;

	// 카테고리 도메인 
	private int catNum = 0;

	// 제목 
	private String title = "";

	// 저자 
	private String author = "";

	// 출판사 
	private String publisher = "";

	// 작성자 
	private String creator = "";

}
