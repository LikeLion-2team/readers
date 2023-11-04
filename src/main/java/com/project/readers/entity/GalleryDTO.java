package com.project.readers.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class GalleryDTO {

    // 갤러리 번호 
    private Long galleryNum;

    // 카테고리 도메인 
    private String catDomain;

    // 제목 
    private String title;

    // 저자 
    private String author;

    // 출판사 
    private String publisher;

    // 작성자 
    private String creator;

    // 점수 번호 
    private int pointNum;

}
