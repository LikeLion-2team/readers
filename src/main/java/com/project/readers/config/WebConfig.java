package com.project.readers.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.project.readers.common.interceptor.AdminCheckInterceptor;
import com.project.readers.common.interceptor.UserCheckInterceptor;
@Configuration
public class WebConfig implements WebMvcConfigurer {
	//설명드리자면 여기 말고 프로퍼티스에서 잡는 방법도 있고 DTO로 만드는 방법도 있어요.
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 확장성
		registry.addInterceptor(checkAdmin()).addPathPatterns("/category/**");
		// 갤러리
		registry.addInterceptor(checkUser()).addPathPatterns("/gallery/**")
		.excludePathPatterns("/gallery/list/**", "/gallery/book/**");
		// 보드
		registry.addInterceptor(checkUser()).addPathPatterns("/board/**")
		.excludePathPatterns("/board/list/**", "/board/view/**");
		// 댓글
		registry.addInterceptor(checkUser()).addPathPatterns("/replies/**")
		.excludePathPatterns("/replies/list/**", "/replies/view/**");
		// 채팅
		registry.addInterceptor(checkUser()).addPathPatterns("/chat/**");
	}

	@Bean
	public AdminCheckInterceptor checkAdmin() {
		return new AdminCheckInterceptor();
	}

	@Bean
	public UserCheckInterceptor checkUser() {
		return new UserCheckInterceptor();
	}

}
