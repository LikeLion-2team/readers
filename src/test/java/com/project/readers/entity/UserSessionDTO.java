package com.project.readers.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class UserSessionDTO {
	 
	 //아이디
	 private String id;
	 //권한 레벨
	 private int roleNum;
	 
}
