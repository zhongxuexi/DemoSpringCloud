package com.jess.member;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableTransactionManagement(order = 2)// 启注解事务管理，指定aop事务执行顺序，保证在切换数据源的后面
@EnableSwagger2
@MapperScan(basePackages = {"com.jess.member.dao"})
@ComponentScan(basePackages = {"com.jess"})
@EnableFeignClients(basePackages = {"com.jess.common"})
public class ServerMemberApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServerMemberApplication.class, args);
	}
}
