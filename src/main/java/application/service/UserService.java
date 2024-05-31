package application.service;

import application.Dto.UserDto;
import application.model.User;

public interface UserService {

	User findByUsername(String username);
	User save(UserDto userDto);
}
