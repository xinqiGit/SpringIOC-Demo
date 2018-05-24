package com.xxx.spring;

import static org.junit.Assert.*;

import org.junit.Test;

import com.xxx.model.User;
import com.xxx.service.UserService;

public class ClassPathXmlApplicationContextTest {

	@Test
	public void testClassPathXmlApplicationContext() throws Exception {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");
		UserService service=(UserService) context.getBean("userService");
		User u =new User();
		u.setName("asd");
		u.setPassword("asd");
		service.add(u);
	}

}
