package com.jess;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableTransactionManagement	// 启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
@EnableSwagger2
@MapperScan(basePackages = {"com.jess.dao"})
@ComponentScan(basePackages = {"com.jess"})
@EnableFeignClients(basePackages = {"com.jess"})
public class ServerMemberApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServerMemberApplication.class, args);
	}
}
