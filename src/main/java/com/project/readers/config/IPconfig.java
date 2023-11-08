package com.project.readers.config;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.project.readers.common.Constant;

import jakarta.servlet.http.HttpSession;

//IP check 방문자 확인
public class IPconfig {

	public static void setIp(HttpSession session) {
		String clientIp = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest().getRemoteAddr();
		session.setAttribute(Constant.CLIENTIP, clientIp);
	}
	public static String getIp(HttpSession session) {
//		String clientIp = (String)session.getAttribute(Constant.CLIENTIP);
		String clientIp = "99999997";
		return clientIp;
	}
}
