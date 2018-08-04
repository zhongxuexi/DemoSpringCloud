package com.jess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ServerFeginApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerFeginApplication.class, args);
	}
}
