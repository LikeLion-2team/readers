package com.project.readers.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import oracle.sql.DATE;
@ToString
@Getter
@Setter
public class VisitorDTO {
 private String ip;
 private DATE visiteTm;
 
}
