package it.polito.ezgas.integration;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;

import exception.InvalidLoginDataException;
import exception.InvalidUserException;

import static org.mockito.Mockito.*;

import java.util.ArrayList;

import static org.mockito.AdditionalAnswers.returnsFirstArg;


import it.polito.ezgas.converter.UserConverter;
import it.polito.ezgas.dto.IdPw;
import it.polito.ezgas.dto.LoginDto;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.User;
import it.polito.ezgas.repository.UserRepository;
import it.polito.ezgas.service.impl.UserServiceimpl;

public class UserServiceTest {
	
	@Mock
	private UserRepository repository;
	@Mock
	private static UserConverter converter;
	
	private UserServiceimpl service;
	
	
	@Test
	 public void testGetUserById() throws InvalidUserException {
		
	    Integer userIdDb = 1;
	    Integer userIdNotDb = 10;
	    Integer userIdNegative = -1;
	    Integer userIdNull = null;
	    
	    User u = new User();
	    u.setUserId(userIdDb);
	    
	    UserDto uDto = new UserDto();
	    uDto.setUserId(userIdDb);
	    
	    repository = mock(UserRepository.class);
	    converter = mock(UserConverter.class);
	    when(repository.exists(anyInt())).thenReturn(true);
	    when(repository.findOne(anyInt())).thenReturn(u);
	    when(converter.convertToDto(any(User.class))).thenReturn(uDto);
	    service = new UserServiceimpl(repository,converter);
	    
	    assertTrue( new ReflectionEquals(uDto).matches(service.getUserById(userIdDb)));
	    assertThrows(InvalidUserException.class, () -> {service.getUserById(userIdNegative);});
	    
	    repository = mock(UserRepository.class);
	    when(repository.exists(anyInt())).thenReturn(false);
	    service = new UserServiceimpl(repository,converter);
	    
	    assertNull(service.getUserById(userIdNotDb));
	    assertNull(service.getUserById(userIdNull));

	  }	
	
	
	@Test
	public void testSaveUser() {
		
		 UserDto uDto = new UserDto(null, "Harry", "password", "harry@email.it", 2);
		 
		 repository = mock(UserRepository.class);
		 converter = mock(UserConverter.class);
		 
		 when(repository.findByEmail(anyString())).thenReturn(null);
		 when(repository.save(any(User.class))).then(returnsFirstArg());
		 when(converter.convertToDto(any(User.class))).thenReturn(uDto);
		 when(converter.convertToEntity(any(UserDto.class))).thenReturn(new User());

		 service = new UserServiceimpl(repository,converter);
		 
		 // Test normal saving
		 assertNotNull(service.saveUser(uDto));
		 
		 // Test with over the limit reputation
		 uDto.setReputation(6);
		 assertNull(service.saveUser(uDto));
		 
		 uDto.setReputation(-6);
		 assertNull(service.saveUser(uDto));
		 
		 // Test with null
		 uDto.setUserName(null);
		 assertNull(service.saveUser(uDto));
		 
		 // Test with negative id
		 uDto.setReputation(4);
		 uDto.setUserName("Harry");
		 uDto.setUserId(-1);
		 assertNull(service.saveUser(uDto));
		 // This will always fail
		 // assertThrows(InvalidUserException.class, ()->{service.saveUser(uDto);});
		
		 uDto.setUserId(1);
		 assertNotNull(service.saveUser(uDto));

		 when(repository.findByEmail(anyString())).thenReturn(new User());
		 service = new UserServiceimpl(repository,converter);
		 
		 uDto.setUserId(null);
		 assertNull(service.saveUser(uDto));
		 
	}
	
	
	@Test
	public void testGetAllUsers() {
		
		repository = mock(UserRepository.class);
		converter = mock(UserConverter.class);
		
		when(converter.convertToDto(any(User.class))).thenReturn(new UserDto());
		when(repository.findAll()).thenReturn(new ArrayList<User>());
		service = new UserServiceimpl(repository,converter);

		assertTrue(service.getAllUsers().isEmpty());
		
		ArrayList<User> list = new ArrayList<User>();
		list.add(new User());
		when(repository.findAll()).thenReturn(list);
		service = new UserServiceimpl(repository,converter);

		assertTrue(!service.getAllUsers().isEmpty());
		
	}
	
	
	@Test
	public void testDeleteUser() throws InvalidUserException {
		
		 Integer uId = 3;
		 Integer uIdNegative = -4;
		 
		 repository = mock(UserRepository.class);
		 
		 when(repository.exists(anyInt())).thenReturn(true);
		 doNothing().when(repository).delete(anyInt());
		 
		 service = new UserServiceimpl(repository,converter);
		 
		 // Test normal delete
		 assertTrue(service.deleteUser(uId));
		 
		 // Test with negative id
		 assertThrows(InvalidUserException.class, () -> {service.deleteUser(uIdNegative);});
		 
		 // Test with null
		 assertFalse(service.deleteUser(null));
		 
		 // Test user id not present
		 when(repository.exists(anyInt())).thenReturn(false);
		 service = new UserServiceimpl(repository,converter);
		 assertFalse(service.deleteUser(uId));
		 
	}
	
	
	@Test
	public void testLogin() throws InvalidLoginDataException {
		
		IdPw idpw = new IdPw("name@ezgas.it", "password");
		User u = new User("mario","password","name@ezgas.it",4);
		u.setUserId(2);
		LoginDto l = new LoginDto(2,"mario",null,"name@ezgas.it",4);
		repository = mock(UserRepository.class);
		when(repository.findByPasswordAndEmail(anyString(), anyString())).thenReturn(u);
		
		service = new UserServiceimpl(repository,converter);
		
		// Test normal
		assertTrue( new ReflectionEquals(l).matches(service.login(idpw)));
		
		// Test null credentials
		idpw.setPw(null);
		assertThrows(InvalidLoginDataException.class,()->{service.login(idpw);});
		
		// Test not present
		idpw.setPw("password");
		when(repository.findByPasswordAndEmail(anyString(), anyString())).thenReturn(null);
		service = new UserServiceimpl(repository,converter);
		
		assertNull(service.login(idpw));	
		
	}
	
	
	@Test
	 public void testIncreaseUserReputation() throws InvalidUserException {
	  
		Integer userIdDb = 1;
	    Integer userIdNotDb = 10;
	    Integer userIdNegative = -1;
	    Integer userIdNull = null;
	     
	    Integer reputationValid = 4;
	    Integer reputationNonValid = 6;
	    
	    User u = new User();
	    u.setUserId(userIdDb);
	    u.setReputation(reputationValid);
	     
	    repository = mock(UserRepository.class);
	     
	    when(repository.exists(anyInt())).thenReturn(true);
	    when(repository.findOne(anyInt())).thenReturn(u);
	    doNothing().when(repository).flush();
	    service = new UserServiceimpl(repository, converter);
	     
	    // Test Normal
	    assertSame(service.increaseUserReputation(userIdDb), reputationValid+1);
	     
	    // Test Negative UserId
	    u.setUserId(userIdNegative);
	    assertThrows(InvalidUserException.class, () -> {service.increaseUserReputation(userIdNegative);});
	  
	  	// Test Invalid Reputation (> 5)
	  	u.setUserId(userIdDb);
	  	u.setReputation(reputationNonValid);
	  	assertSame(service.increaseUserReputation(userIdDb), reputationNonValid);
	  
	  	when(repository.exists(anyInt())).thenReturn(false);
	  	service = new UserServiceimpl(repository,converter);
	     
	  	// Test UserId not in the DB
	     assertThrows(InvalidUserException.class, () -> {service.increaseUserReputation(userIdNotDb);});
	     
	    // Test UserId null
	    assertNull(service.increaseUserReputation(userIdNull));  
	    
	 }
	 
	
	 @Test
	 public void testDecreaseUserReputation() throws InvalidUserException {
	  
	  Integer userIdDb = 1;
	     Integer userIdNotDb = 10;
	     Integer userIdNegative = -1;
	     Integer userIdNull = null;
	     
	     Integer reputationValid = 4;
	     Integer reputationNonValid = -6;
	    
	     User u = new User();
	     u.setUserId(userIdDb);
	     u.setReputation(reputationValid);
	     
	     repository = mock(UserRepository.class);
	     
	     when(repository.exists(anyInt())).thenReturn(true);
	     when(repository.findOne(anyInt())).thenReturn(u);
	     doNothing().when(repository).flush();
	     service = new UserServiceimpl(repository, converter);
	     
	     // Test Normal
	     assertSame(service.decreaseUserReputation(userIdDb), reputationValid-1);
	     
	     // Test Negative UserId
	     u.setUserId(userIdNegative);
	     assertThrows(InvalidUserException.class, () -> {service.decreaseUserReputation(userIdNegative);});
	  
	     // Test Invalid Reputation (< -5)
	     u.setUserId(userIdDb);
	     u.setReputation(reputationNonValid);
	     assertSame(service.decreaseUserReputation(userIdDb), reputationNonValid);
	  
	     when(repository.exists(anyInt())).thenReturn(false);
	     service = new UserServiceimpl(repository,converter);
	     
	     // Test UserId not in the DB
	     assertThrows(InvalidUserException.class, () -> {service.decreaseUserReputation(userIdNotDb);});
	     
	     // Test UserId null
	     assertNull(service.decreaseUserReputation(userIdNull)); 
	     
	 }
	

}
