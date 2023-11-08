package com.project.readers.config;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.project.readers.entity.UserSessionDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class SessionConfig {
	//session 얻어오는 함수
	public static HttpSession getSession() {
		HttpServletRequest httpServletRequest = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		HttpSession session = httpServletRequest.getSession();
		IPconfig.getIp(session);
		return session;
	}

	public static UserSessionDTO getSessionDTO() {
//HttpSession session = getSession();
//		SessionDTO sessionDTO=(SessionDTO)session.getAttribute(ConstantConfig.USER_INFO);
		UserSessionDTO sessionDTO = new UserSessionDTO();
		sessionDTO.setId("admin");
		sessionDTO.setRoleNum(3);
		return sessionDTO;
	}
}
