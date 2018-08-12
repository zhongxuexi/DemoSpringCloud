package com.jess;

import com.google.common.collect.Lists;
import com.jess.commons.api.service.RedisServiceApi;
import com.jess.dao.UserMapper;
import com.jess.commons.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServerMemberApplicationTests {
	@Autowired
	private RedisServiceApi redisServiceApi;
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
			user.setRealname("钟学曦"+i);
			list.add(user);
		}
		//redisService.set("userList",list);
		System.out.println(redisServiceApi.get("userList"));
	}
	@Test
	public void testMapper(){
		for(User user:userMapper.selectAll()){
			System.out.println(user.toString());
		}

	}

}
