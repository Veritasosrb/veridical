package com.tia.bmm.testing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tia.bmm.server.user.User;
import com.tia.bmm.server.user.UserList;

class loginAsUserTest {

	UserList obj = new UserList();
	
	@BeforeEach
	void setUp() throws Exception {
		HashMap<Integer, User> expected = new HashMap<Integer, User>();
		User u = new User(1, "Aditi", "9405308223", "mantriaditi10@gmail.com", "Female");
		expected.put(1, u);
		obj.setUl(expected);
	}

	@Test
	void testLoginAsUser() {
		boolean expected = true;
		boolean actual = obj.loginAsUser();
		
		assertEquals(expected, actual);
	}

}
