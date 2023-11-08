package com.project.readers.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ChatMesgDTO {
  
	// 메세지 아이디 
    private Long mesgId;

    // 채팅 방 아이디 
    private Long chatRoomId;

    // 메세지 내용 
    private String mesgContent;

    private String id;

}
