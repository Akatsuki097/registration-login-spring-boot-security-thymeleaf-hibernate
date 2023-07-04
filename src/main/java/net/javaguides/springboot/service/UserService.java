package net.javaguides.springboot.service;

import net.javaguides.springboot.exception.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

import net.javaguides.springboot.model.User;


import java.util.List;

public interface UserService extends UserDetailsService{
	User save(User registrationDto);

	public List<User> fetchUsers();

	User findByEmail(String email);
	User findByName(String email);

	public User fetchUserById(Long userId) throws UserNotFoundException;

	public String deleteUser(Long userId);

	public User updateUser(Long userId, User user);
}
