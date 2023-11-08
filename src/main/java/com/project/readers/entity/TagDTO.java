package com.project.readers.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class TagDTO {
	//태그 고유 번호
	private int tagNum;
	//태그 그룹
	private String tagGroup;
	//태그 내용
	private String tagContent;
	//태그 자세한 설명
	private String tagInfo;
}
