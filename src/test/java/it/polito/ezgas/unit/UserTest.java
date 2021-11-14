package it.polito.ezgas.unit;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import it.polito.ezgas.entity.User;

public class UserTest {
	
	@Test
	public void testUserId() {
		
		User u = new User();
		
		Integer userId = 32;
		u.setUserId(userId);
		
		assertTrue(u.getUserId() == userId);
		
	
		/*
		 * These asserts will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * Integer userIdNegative = -2;
		 * User uNegative = new User();
		 * uNegative.setUserId(userIdNegative);
		 * assertThrows(InvalidUserException.class, () -> { uNegative.getUserId(); });
		 * 
		 * Integer userIdNull = null;
		 * User uNull = new User();
		 * uNull.setUserId(userIdNull);
		 * assertThrows(InvalidUserException.class, () -> { uNull.getUserId(); }); 
		 */
		
	}
	
	
	@Test
	public void testUserName() {
		
		User u = new User();
		
		String name = "Anne";
		u.setUserName(name);
		
		assertTrue(u.getUserName() == name);
		
		
		/*
		 * This assert will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * String name = null;
		 * u.setUserName(name);
		 * assertThrows(InvalidUserException.class, () -> { u.getUserName(); });
		 */
		
	}

	
	@Test
	public void testUserEmail() {
		
		User u = new User();
		
		String email = "anne@email.it";
		u.setEmail(email);
		
		assertTrue(u.getEmail() == email);
		
	}
	
	
	@Test
	public void testUserPassword() {
		
		User u = new User();
		
		String psw = "password";
		u.setPassword(psw);
		
		assertTrue(u.getPassword() == psw);
		
	}
	
	
	@Test
	public void testUserAdminTrue() {
		
		User u = new User();
		
		Boolean admin = true;
		u.setAdmin(admin);
		
		assertTrue(u.getAdmin() == admin);
		
	}
	
	
	@Test
	public void testUserAdminFalse() {
		
		User u = new User();
		
		Boolean admin = false;
		u.setAdmin(admin);
		
		assertTrue(u.getAdmin() == admin);
		
	}

	
	@Test
	public void testUserReputation() {
		
		User u = new User();
		
		Integer reputation = 4;
		u.setReputation(4);
		
		assertTrue(u.getReputation() == reputation);
		
		
		/*
		 * These asserts will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * Integer reputationAboveMax = 7;
		 * u.setReputation(reputationAboveMax);
		 * assertThrows(InvalidUserException.class, () -> { u.getReputation(); });
		 * 
		 * Integer reputationNull = null;
		 * u.setReputation(reputationNull);
		 * assertThrows(InvalidUserException.class, ()-> { u.getReputation(); });
		 */
	}
	
	
	@Test
	public void testUserConstructor() {
		
		Integer reputation = 4;
		String psw = "password";
		String email = "anne@email.it";
		String name = "Anne";
		
		User u = new User(name, psw, email, reputation);
		
		assertTrue(u.getEmail() == email && u.getUserName() == name
				&& u.getPassword() == psw && u.getReputation() == reputation);
		
	}
	
}
