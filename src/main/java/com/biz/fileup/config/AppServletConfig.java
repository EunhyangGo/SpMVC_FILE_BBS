package com.biz.fileup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

/*
 * servlet-context.xml의 하는일을 대신 Webconfig
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {
			"com.biz.fileup.controller",
			"com.biz.fileup.service"
})
public class AppServletConfig implements WebMvcConfigurer {
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**")
				.addResourceLocations("/resources/");
		
		registry.addResourceHandler("/files/**")
			.addResourceLocations("/files/");
		
		registry.addResourceHandler("/css/**")
			.addResourceLocations("/css/");
		
		WebMvcConfigurer.super.addResourceHandlers(registry);
	}
	
	@Bean
	public BCryptPasswordEncoder encoduer() {
		return new BCryptPasswordEncoder();
	}
	@Bean
	public MultipartResolver multipartResolver() {
		
		CommonsMultipartResolver mr = new CommonsMultipartResolver();
		
		mr.setMaxUploadSize(100000000);
		mr.setMaxUploadSizePerFile(1000000);
		
		return mr;
	}
	/*
	 * @Bean Annotation
	 * 스프링의 내장된 클래스를 사용할 준비를 하라는 지시어
	 */

	@Bean
	public InternalResourceViewResolver resolver() {
		
		InternalResourceViewResolver resolver 
			= new InternalResourceViewResolver();
		
		resolver.setViewClass(JstlView.class);
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		
		return resolver;
	}
}
