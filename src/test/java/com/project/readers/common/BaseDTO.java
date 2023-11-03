package com.project.readers.common;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class BaseDTO {
	protected int pg; //페이지 번호
	protected int rnum; //게시물 식별 번호, 정리된 번호
	protected String searchKey=""; //검색어	
	protected String searchText = "";
}
