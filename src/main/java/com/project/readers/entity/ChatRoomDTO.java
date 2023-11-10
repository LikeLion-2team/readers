package com.project.readers.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class ChatRoomDTO {
    // 채팅 방 아이디
    private Integer chatRoomId = 1;

    // 카테고리 도메인
    private String catDomain;

    // 아이디
    private String id;

    // 채팅 방 이름
    private String chatRoomName;
}
