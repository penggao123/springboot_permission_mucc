package com.mmall;

import com.mmall.utils.ApplicationContextHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class SpringBootPermissionApplication {

	public static void main(String[] args) {
		ApplicationContext run = SpringApplication.run(SpringBootPermissionApplication.class, args);

		ApplicationContextHelper.setApplicationContext(run);

	}




}
