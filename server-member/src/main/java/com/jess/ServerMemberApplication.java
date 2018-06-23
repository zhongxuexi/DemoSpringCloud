package com.jess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ServerMemberApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerMemberApplication.class, args);
	}
}
