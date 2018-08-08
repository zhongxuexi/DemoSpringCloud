package com.jess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages = {"com.jess"})
@EnableFeignClients(basePackages = {"com.jess.commons"})
public class ServerOrderApplication {

//	@Bean
//	@LoadBalanced
//	public RestTemplate restTemplate(){
//		return new RestTemplate();
//	}

	public static void main(String[] args) {
		SpringApplication.run(ServerOrderApplication.class, args);
	}
}
