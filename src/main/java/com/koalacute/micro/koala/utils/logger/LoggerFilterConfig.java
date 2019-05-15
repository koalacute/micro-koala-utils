package com.koalacute.micro.koala.utils.logger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class LoggerFilterConfig {
	
	@Bean
	public Filter logFilter() {
		LoggerRequestFilter filter = new LoggerRequestFilter();
	    return filter;
	}

}
