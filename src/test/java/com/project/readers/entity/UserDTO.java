package com.project.readers.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class UserDTO {

    // 아이디 번호 
    private Long idNum;

    // 아이디 
    private String id;

    // 비밀번호 
    private String pw;

    // 이름 
    private String name;

    // 이메일 
    private String email;
}
