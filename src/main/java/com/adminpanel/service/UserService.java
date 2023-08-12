package com.adminpanel.service;

import com.adminpanel.model.User;
import com.adminpanel.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);

	public void deleteUserById(long id);

	User saveAdmin(UserRegistrationDto adminRegistrationDto);

	void updateUser(Long userId, UserRegistrationDto updatedUser);

}
