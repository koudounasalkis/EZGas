package it.polito.ezgas.unit;

import static org.junit.Assert.assertTrue;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import it.polito.ezgas.entity.User;
import it.polito.ezgas.repository.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;
    
    
    @Test
	public void testFindByEmail() {
		String email = "mario@ezgas.com";
		
		User u = new User();
		u.setEmail(email);
		repository.save(u);
		
		assertTrue(repository.findByEmail(email) == u);
	}
    
    
    @Test
	public void testFindByPasswordAndEmail() {
		String pw = "password";
		String email = "mario@ezgas.com";
		
		User u = new User();
		u.setPassword(pw);
		u.setEmail(email);
		repository.save(u);
		
		assertTrue(repository.findByPasswordAndEmail(pw, email) == u);
	}

}
