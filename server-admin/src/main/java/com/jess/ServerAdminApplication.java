package com.jess;

import de.codecentric.boot.admin.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by zhongxuexi on 2018/8/9.
 */
@SpringBootApplication
@EnableAdminServer
public class ServerAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerAdminApplication.class, args);
    }
}
