package it.polito.ezgas.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import exception.InvalidLoginDataException;
import exception.InvalidUserException;
import it.polito.ezgas.converter.UserConverter;
import it.polito.ezgas.dto.IdPw;
import it.polito.ezgas.dto.LoginDto;
import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.User;
import it.polito.ezgas.repository.UserRepository;
import it.polito.ezgas.service.UserService;

/**
 * Created by softeng on 27/4/2020.
 */

@Service
public class UserServiceimpl implements UserService {
	
	private UserConverter uConverter;
	private UserRepository repository;
	
	public UserServiceimpl (UserRepository userRepository, UserConverter userConverter) {
		this.repository = userRepository;
		this.uConverter = userConverter;
		
	}
	
	
	@Override
	public UserDto getUserById(Integer userId) throws InvalidUserException {
		
		if (userId == null) 
			return null; 

		if (userId < 0) 
			throw new InvalidUserException("User ID non-valid.");
		
		if (repository.exists(userId))
			return uConverter.convertToDto(repository.findOne(userId));
		else 
			return null;
		
	}

	
	@Override
	public UserDto saveUser(UserDto userDto) {
		
		if (userDto.getAdmin() == null || userDto.getEmail() == null || userDto.getPassword() == null 
				|| userDto.getUserName() == null || userDto.getReputation() == null)
			return null;
		
		if (userDto.getUserId() != null && userDto.getUserId() < 0)
			//should throw an exception
			return null; 
		
		if (userDto.getReputation() < -5 || userDto.getReputation() > 5)
			return null;
		
		if (repository.findByEmail(userDto.getEmail()) != null && userDto.getUserId() == null) 
			return null;
		else
			return uConverter.convertToDto(repository.save(uConverter.convertToEntity(userDto)));
		
	}

	
	@Override
	public List<UserDto> getAllUsers() {
		return repository.findAll().stream().map(uConverter::convertToDto).collect(Collectors.toList());
		
	}

	
	@Override
	public Boolean deleteUser(Integer userId) throws InvalidUserException {
		
		if (userId == null)
			return false;
		
		if (userId < 0) 
			throw new InvalidUserException("User ID non-valid");
		
		if (repository.exists(userId)) {
			repository.delete(userId);
			return true;
		} else 
			return false;
		
	}

	
	@Override
	public LoginDto login(IdPw credentials) throws InvalidLoginDataException {

		if (credentials.getPw() == null || credentials.getUser() == null)
			throw new InvalidLoginDataException ("Invalid Login Data");
		
		User u = repository.findByPasswordAndEmail(credentials.getPw(),credentials.getUser());
		if (u == null)
			return null;
		LoginDto uLog = new LoginDto();
		uLog.setEmail(u.getEmail());
		uLog.setReputation(u.getReputation());
		uLog.setAdmin(u.getAdmin());
		uLog.setUserId(u.getUserId());
		uLog.setUserName(u.getUserName());
		return uLog;
		
	}

	
	@Override
	public Integer increaseUserReputation(Integer userId) throws InvalidUserException {
		
		if (userId == null)
			return null;
		
		if (userId < 0) 
			throw new InvalidUserException ("User ID non-valid");
		
		if (repository.exists(userId)) {
			User u = repository.findOne(userId);
			Integer rep = u.getReputation();
			if (rep < 5) {
				rep += 1;
				u.setReputation(rep);
			    repository.flush();
			}
			return rep;
		} else
			throw new InvalidUserException ("User ID non existing");
		
	}

	
	@Override
	public Integer decreaseUserReputation(Integer userId) throws InvalidUserException {
		
		if (userId == null)
			return null;
	
		if (userId < 0) 
			throw new InvalidUserException ("User ID non-valid");
	
		if (repository.exists(userId)) {
			User u = repository.findOne(userId);
			Integer rep = u.getReputation();
			if (rep > -5) {
				rep -= 1;
				u.setReputation(rep);
				repository.flush();
			}
			return rep;
		} else
			throw new InvalidUserException ("User ID non existing");
		
	}
	
}
