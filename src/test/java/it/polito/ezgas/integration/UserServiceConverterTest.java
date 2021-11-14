package it.polito.ezgas.integration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import exception.InvalidUserException;

import static org.mockito.Mockito.*;

import java.util.ArrayList;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;

import it.polito.ezgas.converter.UserConverter;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.User;
import it.polito.ezgas.repository.UserRepository;
import it.polito.ezgas.service.impl.UserServiceimpl;

public class UserServiceConverterTest {
	
	private UserServiceimpl service;
	private UserConverter converter = new UserConverter();

	@Mock
	private UserRepository repository;

	
	@Test
	  public void testGetUserById() throws InvalidUserException {
		
	      Integer userIdDb = 1;
	      Integer userIdNotDb = 10;
	      Integer userIdNegative = -1;
	      Integer userIdNull = null;
	      
	      User u = new User("mario", "password", "name@ezgas.it", 4);
	      u.setUserId(userIdDb);
	      u.setAdmin(false);
	      
	      UserDto uDto = new UserDto(userIdDb,"mario", "password", "name@ezgas.it", 4, false);
	      
	      repository = mock(UserRepository.class);

	      when(repository.exists(anyInt())).thenReturn(true);
	      when(repository.findOne(anyInt())).thenReturn(u);
	      service = new UserServiceimpl(repository,converter);
	      
	      // userId present
	      assertTrue(new ReflectionEquals(uDto).matches(service.getUserById(userIdDb)));
	      
	      // userId negative
	      assertThrows(InvalidUserException.class, () -> {service.getUserById(userIdNegative);});
	      
	      // UserId not present
	      repository = mock(UserRepository.class);
	      when(repository.exists(anyInt())).thenReturn(false);
	      service = new UserServiceimpl(repository,converter);
	      assertNull(service.getUserById(userIdNotDb));
	      
	      // user Id null
	      assertNull(service.getUserById(userIdNull));
	    }
	
	
	@Test
	public void testSaveUser() {
		
		 UserDto uDto = new UserDto(null, "Harry", "password", "harry@email.it", 2);
		 
		 repository = mock(UserRepository.class);
		 
		 when(repository.findByEmail(anyString())).thenReturn(null);
		 when(repository.save(any(User.class))).then(returnsFirstArg());

		 service = new UserServiceimpl(repository,converter);
		 
		 // Test Normal Saving Case
		 assertNotNull(service.saveUser(uDto));
		 
		 // Test Non-Valid Reputation (> 5) Case
		 uDto.setReputation(6);
		 assertNull(service.saveUser(uDto));
		 
		 // Test Non-Valid Reputation (< -5) Case
		 uDto.setReputation(-6);
		 assertNull(service.saveUser(uDto));
		 
		 // Test UserName NULL Case
		 uDto.setUserName(null);
		 assertNull(service.saveUser(uDto));
		 
		 // Test Invalid UserID Case
		 uDto.setReputation(4);
		 uDto.setUserName("Harry");
		 uDto.setUserId(-1);
		 assertNull(service.saveUser(uDto));
		 // This will always fail
		 // assertThrows(InvalidUserException.class, ()->{service.saveUser(uDto);});
		 		
		 // Test Updating Case
		 uDto.setUserId(1);
		 assertNotNull(service.saveUser(uDto));

		 // Test Saving a user with an already existing email Case
		 when(repository.findByEmail(anyString())).thenReturn(new User());
		 service = new UserServiceimpl(repository,converter);
		 uDto.setUserId(null);
		 assertNull(service.saveUser(uDto));
	}
	
	
	@Test
	public void testGetAllUsers() {
		
		repository = mock(UserRepository.class);
		when(repository.findAll()).thenReturn(new ArrayList<User>());
		service = new UserServiceimpl(repository, converter);

		// Test Empty List
		assertTrue(service.getAllUsers().isEmpty());
		
		ArrayList<User> list = new ArrayList<User>();
		list.add(new User());
		when(repository.findAll()).thenReturn(list);
		service = new UserServiceimpl(repository, converter);

		// Test Non-Empty List
		assertFalse(service.getAllUsers().isEmpty());
	}

}
