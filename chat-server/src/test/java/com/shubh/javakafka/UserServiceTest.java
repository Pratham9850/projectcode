package com.shubh.javakafka;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import com.shubh.kafkachat.service.UserService;

class UserServiceTest {

//	@Test
//	void test() {
//		fail("Not yet implemented");
//	}
	
	 private UserService userService;

	    @BeforeEach
	    void setUp() {
	        userService = new UserService();
	    }

	    @Test
	    void testAddUser() {
	        userService.addUser("user1");
	        List<String> onlineUsers = userService.getOnlineUsers();
	        assertEquals(1, onlineUsers.size());
	        assertTrue(onlineUsers.contains("user1"));
	    }
	    
	    @Test
	    void testRemoveUser() {
	        userService.addUser("user1");
	        userService.removeUser("user1");
	        List<String> onlineUsers = userService.getOnlineUsers();
	        assertEquals(0, onlineUsers.size());
	    }
	    
	   
}
