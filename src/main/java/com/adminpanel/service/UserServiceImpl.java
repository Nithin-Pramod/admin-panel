package com.adminpanel.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import com.adminpanel.model.Role;
import com.adminpanel.model.User;
import com.adminpanel.dto.UserRegistrationDto;
import com.adminpanel.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.adminpanel.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public User save(UserRegistrationDto registrationDto) {
		User user = new User(registrationDto.getFirstName(), 
				registrationDto.getLastName(), registrationDto.getEmail(),
				passwordEncoder.encode(registrationDto.getPassword()), Arrays.asList(new Role("ROLE_USER")));
		
		return userRepository.save(user);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		User user = userRepository.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));		
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	@Override
	public void deleteUserById(long id) {
		this.userRepository.deleteById(id);
	}


	@Override
	public User saveAdmin(UserRegistrationDto adminRegistrationDto) {
		User admin = new User(adminRegistrationDto.getFirstName(),
				adminRegistrationDto.getLastName(), adminRegistrationDto.getEmail(),
				passwordEncoder.encode(adminRegistrationDto.getPassword()),
				Arrays.asList(new Role("ROLE_ADMIN")));
		return userRepository.save(admin);
	}

	@Override
	@Transactional
	public void updateUser(Long userId, UserRegistrationDto updatedUser) {
		User user = userRepository.findById(userId).orElse(null);

		if (user != null) {
			user.setFirstName(updatedUser.getFirstName());
			user.setLastName(updatedUser.getLastName());
			user.setEmail(updatedUser.getEmail());

			// Update other user fields as needed

			userRepository.save(user);
		}
	}

}
