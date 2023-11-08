package com.project.readers.config;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.project.readers.common.Constant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

//IP check 방문자 확인
public class IPconfig {

	public static void inputSessionIp(HttpSession session) {
		String clientIp = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getRemoteAddr();
		session.setAttribute(Constant.USER_INFO, clientIp);
	}
	public static String getIp(HttpSession session) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		return request.getRemoteAddr();
	}
}
