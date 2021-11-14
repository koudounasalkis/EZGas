package it.polito.ezgas.unit;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import it.polito.ezgas.dto.IdPw;

public class IdPwDtoTest {
	
	@Test
	public void testIdPwUserDto() {
		String user = "user1@ezgas.it";
		
		IdPw credentials = new IdPw();
		credentials.setUser(user);
		assertTrue(credentials.getUser() == user);
		
		
		/*
		 * This assert will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * String userNull = null;
		 * credentials.setUser(user);
		 * assertThrows(InvalidUserException.class, ()-> { credentials.getUser(); });
		 */
	}
	
	
	@Test
	public void testIdPwPasswordDto() {
		String password = "password";
		
		IdPw credentials = new IdPw();
		credentials.setPw(password);
		assertTrue(credentials.getPw() == password);

		
		/*
		 * This assert will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * String passwordNull = null;
		 * credentials.setPw(password);
		 * assertThrows(InvalidUserException.class, ()-> { credentials.getPw(); });
		 */
	}

	
	@Test
	public void testIdPwConstructorDto() {
		String user = "user1@ezgas.it";
		String password = "password";
		
		IdPw credentials = new IdPw(user, password);
		assertTrue(credentials.getPw() == password && credentials.getUser() == user);
	}

}
