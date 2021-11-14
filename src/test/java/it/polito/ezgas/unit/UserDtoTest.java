package it.polito.ezgas.unit;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import it.polito.ezgas.dto.UserDto;

public class UserDtoTest {
	
	@Test
	public void testUserIdDto() {
		
		Integer userIdPositive = 2;

		UserDto uDtoPositive = new UserDto();
		uDtoPositive.setUserId(userIdPositive);
		
		assertTrue(uDtoPositive.getUserId() == userIdPositive);
		
		/*
		 * These asserts will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * Integer userIdNegative = -2;
		 * UserDto uDtoNegative = new UserDto();
		 * uDtoNegative.setUserId(userIdNegative);
		 * assertThrows(InvalidUserException.class, () -> { uDtoNegative.getUserId(); });
		 * 
		 * Integer userIdNull = null;
		 * UserDto uDtoNull = new UserDto();
		 * uDtoNull.setUserId(userIdNull);
		 * assertThrows(InvalidUserException.class, () -> { uDtoNull.getUserId(); });
		 */
		
	}
	
	
	@Test
	public void testUserNameDto() {
		
		String name = "Frank";

		UserDto uDto = new UserDto();
		uDto.setUserName(name);
		
		assertTrue(uDto.getUserName() == name);
		
		
		/*
		 * This assert will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * String nameNull = null;
		 * UserDto uDtoNull = new UserDto();
		 * uDtoNull.setUserName(nameNull);
		 * assertThrows(InvalidUserException.class, ()-> { uDto.getUserName(); });
		 */		

	}

	
	@Test
	public void testUserEmailDto() {
		
		String email = "frank@email.it";

		UserDto uDto = new UserDto();
		uDto.setEmail(email);
		
		assertTrue(uDto.getEmail() == email);
		
	}
	
	
	@Test
	public void testUserPasswordDto() {
		
		String password = "password";
		
		UserDto uDto = new UserDto();
		uDto.setPassword(password);
		
		assertTrue(uDto.getPassword() == password);
		
	}
	
	
	@Test
	public void testUserAdminDto() {
		
		Boolean adminYes = true;
		Boolean adminNo = false;

		UserDto uDtoYes = new UserDto();
		uDtoYes.setAdmin(adminYes);
		UserDto uDtoNo = new UserDto();
		uDtoNo.setAdmin(adminNo);
		
		assertTrue(uDtoYes.getAdmin() == adminYes);
		assertTrue(uDtoNo.getAdmin() == adminNo);
		
	}

	
	@Test
	public void testUserReputationDto() {
		
		Integer reputation = 4;

		UserDto uDto = new UserDto();
		uDto.setReputation(4);
		
		assertTrue(uDto.getReputation() == reputation);
		
		
		/*
		 * This assert will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * Integer reputationAboveMax = 10;
		 * uDto.setReputation(reputationAboveMax);
		 * assertThrows(InvalidUserException.class, ()-> { uDto.getReputation(); });
		 * 
		 * 
		 * Integer reputationBelowMin = -10;
		 * uDto.setReputation(reputationBelowMin);
		 * assertThrows(InvalidUserException.class, ()-> { uDto.getReputation(); });
		 */	
		
	}
	
	
	@Test
	public void testUserConstructor1Dto() {
		
		Integer userId = 2;
		String name = "Anne";
		String password = "password";
		String email = "anne@email.it";
		Integer reputation = 4;
		
		UserDto uDto = new UserDto(userId, name, password, email, reputation);
		assertTrue(uDto.getUserId() == userId && uDto.getEmail() == email && uDto.getUserName() == name
				&& uDto.getPassword() == password && uDto.getReputation() == reputation);
		
	}
	
	
	@Test
	public void testUserConstructor2Dto() {
		
		Integer userId = 2;
		String name = "Anne";
		String password = "password";
		String email = "anne@email.it";
		Integer reputation = 4;
		Boolean admin = true;
		
		UserDto uDto = new UserDto(userId, name, password, email, reputation, admin);
		assertTrue(uDto.getUserId() == userId && uDto.getEmail() == email 
				&& uDto.getUserName() == name && uDto.getPassword() == password 
				&& uDto.getReputation() == reputation && uDto.getAdmin() == admin);
		
	}
	
}
