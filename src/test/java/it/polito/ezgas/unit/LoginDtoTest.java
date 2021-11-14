package it.polito.ezgas.unit;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import it.polito.ezgas.dto.LoginDto;

public class LoginDtoTest {
	
	
	@Test
	public void testLoginIdDto() {
		Integer userIdPositive = 2;

		LoginDto lDtoPositive = new LoginDto();
		lDtoPositive.setUserId(userIdPositive);
				
		assertTrue(lDtoPositive.getUserId() == userIdPositive);
		
		
		/*
		 * This assert will always fail in the current version (because the checks are done at higher level, in the service package)
		 * 
		 * Integer userIdNegative = -2;
		 * LoginDto lDtoNegative = new LoginDto();
		 * lDtoNegative.setUserId(userIdNegative);
		 * assertThrows(InvalidLoginDataException.class, () -> { lDtoNegative.getUserId(); });
		 */
		
	}
	
	
	@Test
	public void testLoginUserNameDto() {
		String name = "James";

		LoginDto lDto = new LoginDto();
		lDto.setUserName(name);
		
		assertTrue(lDto.getUserName() == name);
	}

	
	@Test
	public void testLoginEmailDto() {
		String email = "james@email.it";

		LoginDto lDto = new LoginDto();
		lDto.setEmail(email);
		
		assertTrue(lDto.getEmail() == email);
	}
	
	
	@Test
	public void testLoginTokenDto() {
		String token = "token?";
		
		LoginDto lDto = new LoginDto();
		lDto.setToken(token);
		
		assertTrue(lDto.getToken() == token);
	}
	
	
	@Test
	public void testLoginAdminDto() {
		Boolean adminYes = true;
		Boolean adminNo = false;

		LoginDto lDtoYes = new LoginDto();
		lDtoYes.setAdmin(adminYes);
		LoginDto lDtoNo = new LoginDto();
		lDtoNo.setAdmin(adminNo);
		
		assertTrue(lDtoYes.getAdmin() == adminYes);
		assertTrue(lDtoNo.getAdmin() == adminNo);
	}

	
	@Test
	public void testLoginReputationDto() {
		Integer reputation = 4;

		LoginDto lDto = new LoginDto();
		lDto.setReputation(4);
		
		assertTrue(lDto.getReputation() == reputation);
	}
	
	
	@Test
	public void testLoginConstructorDto() {
		Integer userId = 2;
		String name = "James";
		String token = "token?";
		String email = "james@email.it";
		Integer reputation = 4;

		LoginDto uDto = new LoginDto(userId, name, token, email, reputation);
		
		assertTrue(uDto.getUserId() == userId && uDto.getEmail() == email && uDto.getUserName() == name
				&& uDto.getToken() == token && uDto.getReputation() == reputation);
	}
}
