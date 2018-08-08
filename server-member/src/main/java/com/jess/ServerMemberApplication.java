package com.jess;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@EnableTransactionManagement	// 启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
@MapperScan(basePackages = {"com.jess.dao"})
@ComponentScan(basePackages = {"com.jess"})
@EnableFeignClients(basePackages = {"com.jess.commons"})
public class ServerMemberApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServerMemberApplication.class, args);
	}
}
