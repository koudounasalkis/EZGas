package it.polito.ezgas.integration;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import exception.InvalidLoginDataException;
import exception.InvalidUserException;

import it.polito.ezgas.converter.UserConverter;
import it.polito.ezgas.dto.IdPw;
import it.polito.ezgas.dto.LoginDto;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.User;
import it.polito.ezgas.repository.UserRepository;
import it.polito.ezgas.service.impl.UserServiceimpl;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserServiceConverterRepositoryTest {
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	private UserConverter converter = new UserConverter();
	
	private UserServiceimpl service;
	
	@Test
	public void testGetUserById() throws InvalidUserException {
		
		User u = new User("test", "password", "test@email.it", 4);
		entityManager.persist(u);
		UserDto uDto = new UserDto();
		uDto.setEmail(u.getEmail());
		uDto.setPassword(u.getPassword());
		uDto.setReputation(u.getReputation());
		uDto.setUserId(u.getUserId());
		uDto.setUserName(u.getUserName());
		
		service = new UserServiceimpl(repository, converter);
		
		// Test Normal Case: find User given his UserId 
		assertTrue(new ReflectionEquals(uDto).matches(service.getUserById(u.getUserId())));
		
		// Test Negative UserId
		assertThrows(InvalidUserException.class, () -> {service.getUserById(-4); });
		
		// Test UserId not present in the DB
		assertTrue(service.getUserById(4) == null);
		
		// Test null UserID
		assertTrue(service.getUserById(null) == null);		
	}
	
	
	@Test
	public void testSaveUser() {
		
		 UserDto uDto = new UserDto(null, "Harry", "password", "harry@email.it", 2);
		 User u = new User();
		 u.setEmail("harry@email.it");
		 u.setPassword("password");
		 u.setReputation(2);
		 u.setUserId(null);
		 u.setUserName("Harry");
		 
		 service = new UserServiceimpl(repository, converter);
		 
		 // Test Normal Case: saving a User 
		 assertTrue(service.saveUser(uDto) != null);
		 
		 // Test UserReputation over the limit (>5)
		 uDto.setReputation(6);
		 assertTrue(service.saveUser(uDto)==null);
		 
		 // Test UserReputation over the limit (<-5)
		 uDto.setReputation(-6);
		 assertTrue(service.saveUser(uDto)==null);
		 
		 // Test Negative UserId
		 uDto.setReputation(3);
		 uDto.setUserId(-3);
		 assertNull(service.saveUser(uDto));
		 // This will always fail
		 // assertThrows(InvalidUserException.class, ()->{service.saveUser(uDto);});
		 
		 // Test with null field
		 uDto.setUserName(null);
		 uDto.setUserId(1);
		 assertTrue(service.saveUser(uDto) == null);
		 
		 // Test to update data
		 User u2 = new User("mario", "password", "admin@ezgas.com", 5);
		 entityManager.persist(u2);
		 String passwordu2 = u2.getPassword();
		 
		 UserDto uDto2 = new UserDto(u2.getUserId(), u2.getUserName(), "1234", u2.getEmail(), u2.getReputation());//->>> I'm changing only the password

		 service = new UserServiceimpl(repository, converter);

		 assertTrue(u2.getUserId() == service.saveUser(uDto2).getUserId() 
				 && passwordu2 != service.saveUser(uDto2).getPassword());
		 
		 // Test with duplicate email
		 UserDto uDto3 = new UserDto(null, "Ron", "0000", "harry@email.it", 0);
		 User u3 = new User();
		 u3.setEmail(uDto3.getEmail());
		 u3.setPassword(uDto3.getPassword());
		 u3.setReputation(uDto3.getReputation());
		 u3.setUserId(uDto3.getUserId());
		 u3.setUserName(uDto3.getUserName());

		 service = new UserServiceimpl(repository, converter);
		 assertTrue(service.saveUser(uDto3)==null);		 
	}
	
	
	@Test
	public void testGetAllUsers() {
		
		service = new UserServiceimpl(repository, converter);
		assertTrue(service.getAllUsers().isEmpty());
		
		User u1 = new User("mario", "password", "admin@ezgas.com", 5);
		entityManager.persist(u1);
		User u2 = new User("test", "password", "test@email.it", 4);
		entityManager.persist(u2);
		
		service = new UserServiceimpl(repository, converter);
		
		// Test that returns non empty list
		assertTrue(!service.getAllUsers().isEmpty());
	}
	
	
	@Test
	public void testDeleteUser() throws InvalidUserException {
		
		User u = new User("mario", "password", "admin@ezgas.com", 5);
		entityManager.persist(u);
			
		service = new UserServiceimpl(repository,converter);
		
		// Test normal delete
		assertTrue(service.deleteUser(u.getUserId()));
		
		// Test with negative id
		assertThrows(InvalidUserException.class, () -> {service.deleteUser(-1);});
		
		// Test with null id
		assertTrue(!service.deleteUser(null));
		
		// Test with not present id
		assertTrue(!service.deleteUser(100));
	}
	
	
	@Test
	public void testLogin() throws InvalidLoginDataException {
		
		User u = new User("mario", "password", "name@ezgas.it", 4);
		entityManager.persist(u);
		IdPw idpw = new IdPw("name@ezgas.it","password");
		LoginDto log = new LoginDto(u.getUserId(), "mario", null, "name@ezgas.it", 4);
		
		service = new UserServiceimpl(repository,converter);
		
		// Test normal login
		assertTrue( new ReflectionEquals(log).matches(service.login(idpw)));
		
		// Test null credentials
		idpw.setPw(null);
		assertThrows(InvalidLoginDataException.class,()->{service.login(idpw);});
		
		// Test not present
		idpw.setPw("1234");		
		assertTrue(service.login(idpw)==null);		
	}
	
	
	@Test
	public void testIncreaseUserReputation() throws InvalidUserException {
		
		Integer reputationValid = 4;
		Integer reputationInvalid = 6;
		
		User u = new User("mario","password","name@ezgas.it",reputationValid);
		entityManager.persist(u);
		
		service = new UserServiceimpl(repository,converter);
		
		// Test normal
		assertSame(service.increaseUserReputation(u.getUserId()), reputationValid+1);
		
		// Test negative user id
		assertThrows(InvalidUserException.class, () -> { service.increaseUserReputation(-1); });
		
		// Test Invalid Reputation (> 5)
		User u2 = new User("harry","1234","harry@ezgas.it",reputationInvalid);
		entityManager.persist(u2);
	  	assertSame(service.increaseUserReputation(u2.getUserId()), reputationInvalid);
	  	
	  	// Test UserId not in the DB
		assertThrows(InvalidUserException.class, () -> { service.increaseUserReputation(100); });
	    
	    // Test UserId null
	    assertTrue(service.increaseUserReputation(null)==null);  
	  	
	}
	
	
	@Test
	public void testDecreaseUserReputation() throws InvalidUserException {
		
		Integer reputationValid = 4;
		Integer reputationInvalid = -6;
		
		User u = new User("mario","password","name@ezgas.it",reputationValid);
		entityManager.persist(u);
		
		service = new UserServiceimpl(repository,converter);
		
		//Test normal
		assertSame(service.decreaseUserReputation(u.getUserId()), reputationValid-1);
		
		//Test negative user id
		assertThrows(InvalidUserException.class, () -> { service.decreaseUserReputation(-1); });
		
		// Test Invalid Reputation (< -5)
		User u2 = new User("harry","1234","harry@ezgas.it",reputationInvalid);
		entityManager.persist(u2);
	  	assertSame(service.decreaseUserReputation(u2.getUserId()), reputationInvalid);
	  	
	  	// Test UserId not in the DB
		assertThrows(InvalidUserException.class, () -> { service.decreaseUserReputation(100); });
	    
	    // Test UserId null
	    assertTrue(service.decreaseUserReputation(null) == null);  	
	}
	
}