package com.koalacute.micro.koala.utils.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


/**  
* @Title: CrossOriginConfiguration.java
* @Package com.kakaloans.micro.common
* @Description: 解决springmvc跨域访问
* @author panzongwei 
* @date 2017年7月6日 下午4:31:15
* @version V1.0  
*/ 
@Configuration
public class CrossOriginConfiguration {

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedHeaders("*")
						.allowedMethods("*")
						.allowedOrigins("*");
			}
		};
	}

}
