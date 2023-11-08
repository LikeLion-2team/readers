package com.project.readers.entity;

import com.project.readers.common.BaseDTO;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDTO extends BaseDTO {

    // 게시글 번호 
    private Integer boardNum;

    // 카테고리 도메인 
    private Integer catNum;

    // 작성자 
    private String creator;

    // 제목
    @NotEmpty
    private String title;

    // 내용
    @NotEmpty
    private String content;

    // 조회수 
    private Long hit;

    // 작성일
    private LocalDate crtTm;
}
