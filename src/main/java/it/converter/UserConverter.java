package it.polito.ezgas.converter;

import org.springframework.stereotype.Component;

import it.polito.ezgas.dto.UserDto;
import it.polito.ezgas.entity.User;

@Component
public class UserConverter {
	public UserDto convertToDto(User user) {
		return new UserDto(
				user.getUserId(),
				user.getUserName(),
				user.getPassword(),
				user.getEmail(),
				user.getReputation(),
				user.getAdmin());
	}
	
	public User convertToEntity(UserDto userDto) {
		User u =new User(
				userDto.getUserName(),
				userDto.getPassword(),
				userDto.getEmail(),
				userDto.getReputation());
		u.setAdmin(userDto.getAdmin());
		u.setUserId(userDto.getUserId());
//		if(userDto.getUserId()!=null) {
//			u.setUserId(userDto.getUserId());
//		}
		return u;
	}
}
