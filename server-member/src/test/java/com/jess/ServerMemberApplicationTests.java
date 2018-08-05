package com.jess;

import com.google.common.collect.Lists;
import com.jess.commons.api.service.RedisService;
import com.jess.dao.msg.UserMapper;
import com.jess.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@MapperScan("com.jess.dao")
public class ServerMemberApplicationTests {
	@Autowired
	private RedisService redisService;
	@Autowired
	private UserMapper userMapper;
	@Test
	public void testRedis() {
		List<User> list = Lists.newArrayList();
		for (int i = 0;i<3;i++){
			User user = new User();
			user.setId((long) i);
			user.setAge((byte) 45);
			user.setEducation("本科");
			user.setRealName("钟学曦"+i);
			list.add(user);
		}
		//redisService.set("userList",list);
		System.out.println(redisService.get("userList"));
	}
	@Test
	public void testMapper(){
		List<User> users = userMapper.selectAll();
	}

}
