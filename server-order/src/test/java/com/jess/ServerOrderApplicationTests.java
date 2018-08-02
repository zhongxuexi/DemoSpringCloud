package com.jess;

import com.jess.commons.api.service.TestService;
import com.jess.commons.api.service.TestServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServerOrderApplicationTests {
	@Autowired
	private TestService testService;

	@Test
	public void contextLoads() {
		System.out.println("获得的："+testService.findString());
	}

	@Test
	public void test1(){
		System.out.println("获得的："+new TestServiceImpl().findString());
		System.out.println("ddddd");
	}

}
