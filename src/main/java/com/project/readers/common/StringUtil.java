package com.project.readers.common;
public class StringUtil {
	public static String nullToVal(Object o, String value) {
		if(o==null) {
			return value;
		}else {
			return String.valueOf(o);
		}
	}
}
