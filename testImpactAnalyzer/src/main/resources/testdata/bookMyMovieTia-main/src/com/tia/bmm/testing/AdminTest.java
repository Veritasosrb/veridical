package com.tia.bmm.testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tia.bmm.server.admin.Admin;

class AdminTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testLoginAsAdmin() {
		boolean expected = true;
		Admin obj = new Admin();
		boolean actual = obj.loginAsAdmin();
		
		assertEquals(expected, actual);
	}

}
