package com.imooc.miaosha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class MiaoshaApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MiaoshaApplication.class, args);
	}

	@Override
	public SpringApplicationBuilder configure(SpringApplicationBuilder builder) { //打成war包得加这个
		return builder.sources(MiaoshaApplication.class);
	}
}
