package com.jess.member;

import com.google.common.collect.Lists;
//import com.jess.common.component.redis.RedisClient;
import com.jess.member.dao.UserMapper;
import com.jess.member.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServerMemberApplicationTests {
	//@Autowired
	//private RedisClient redisClient;
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
		//System.out.println(redisClient.get("userList"));
	}
	@Test
	public void testMapper(){
		for(User user:userMapper.selectAll()){
			System.out.println(user.toString());
		}
	}
}
