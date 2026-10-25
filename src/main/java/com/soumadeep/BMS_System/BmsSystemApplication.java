package com.soumadeep.BMS_System;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = { UserDetailsServiceAutoConfiguration.class })
public class BmsSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BmsSystemApplication.class, args);
	}
}
