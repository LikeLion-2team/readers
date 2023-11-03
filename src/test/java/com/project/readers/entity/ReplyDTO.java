package com.project.readers.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ReplyDTO {
   
	// 댓글 번호 
    private Long rplNum;

    // 게시글 번호 
    private Long boardNum;

    // 작성자 
    private String creator;

    // 댓글 내용 
    private String rplContent;

    // 뎁스 
    private Integer depth;


}
