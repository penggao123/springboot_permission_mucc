package com.mmall;

import com.mmall.common.ApplicationContextHelper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
//@MapperScan(value = "com.mmall.dao")
public class SpringBootPermissionApplication {

	public static void main(String[] args) {
		ApplicationContext run = SpringApplication.run(SpringBootPermissionApplication.class, args);

		ApplicationContextHelper.setApplicationContext(run);

	}




}
