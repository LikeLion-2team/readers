package com.project.readers.common.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.project.readers.common.Constant;
import com.project.readers.config.SessionConfig;
import com.project.readers.config.UserRoleConfig.UserRole;
import com.project.readers.entity.UserSessionDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AdminCheckInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		Integer roleNum = handleSessionDTO();

		if (roleNum != UserRole.ADMIN.getLevel()) {
			response.sendRedirect("/");
			return false;
		} else if (roleNum == UserRole.ADMIN.getLevel()) {
			return true;
		} else {
			throw new IllegalArgumentException(Constant.UNKNOWN_ACCESS);
		}
	}

	private Integer handleSessionDTO() {
		Integer roleNum = UserRole.GUEST.getLevel();
		UserSessionDTO userSessionDTO = SessionConfig.getSessionDTO();
		if (userSessionDTO == null)
			roleNum = null;
		else
			roleNum = userSessionDTO.getRoleNum();
		return roleNum;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
	}

}
