package com.jess;

import com.github.pagehelper.PageHelper;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@SpringBootApplication
@EnableEurekaClient
@EnableTransactionManagement	// 启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
public class ServerMemberApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServerMemberApplication.class, args);
	}
}
