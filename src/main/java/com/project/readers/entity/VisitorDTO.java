package com.project.readers.entity;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@ToString
@Getter
@Setter
public class VisitorDTO {
 private String ip;
 private LocalDateTime visiteTm;
 
}
