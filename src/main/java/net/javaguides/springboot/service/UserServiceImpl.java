package net.javaguides.springboot.service;

import java.util.*;
import java.util.stream.Collectors;

import net.javaguides.springboot.exception.UserNotFoundException;
import net.javaguides.springboot.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import net.javaguides.springboot.model.User;
import net.javaguides.springboot.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService{

	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	@Override
	public User save(User user) {
//		User user = new User(registrationDto.getFirstName(),
//				registrationDto.getLastName(), registrationDto.getEmail(),
//				passwordEncoder.encode(registrationDto.getPassword()),);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		
		return userRepository.save(user);
	}

	@Override
	public List<User> fetchUsers() {
		return userRepository.findAll();
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

	@Override
	public User findByName(String userName) {
		return userRepository.findByUsername(userName);
	}

	@Override
	public User fetchUserById(Long userId) throws UserNotFoundException {
		Optional<User> user = userRepository.findById(userId);
		if(!user.isPresent()){
			throw new UserNotFoundException("User Not Found");
		}
		return user.get();
	}

	@Override
	public String deleteUser(Long userId) {
		userRepository.deleteById(userId);
		return "Delete Successful";
	}

	@Override
	public User updateUser(Long userId, User user) {
		User userDB = userRepository.findById(userId).get();

		if(Objects.nonNull(user.getEmail()) && !"".equalsIgnoreCase(user.getEmail())){
			userDB.setEmail(user.getEmail());
		}
		if(Objects.nonNull(user.getUsername()) && !"".equalsIgnoreCase(user.getUsername())){
			userDB.setUsername(user.getUsername());
		}
		if(Objects.nonNull(user.getFirstName()) && !"".equalsIgnoreCase(user.getFirstName())){
			userDB.setFirstName(user.getFirstName());
		}
		if(Objects.nonNull(user.getLastName()) && !"".equalsIgnoreCase(user.getLastName())){
			userDB.setLastName(user.getLastName());
		}
		if(Objects.nonNull(user.getPassword()) && !"".equalsIgnoreCase(user.getPassword())){
			userDB.setPassword(user.getPassword());
		}
		return userRepository.save(userDB);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	
		User user = userRepository.findByEmail(username);
		if(user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), mapRolesToAuthorities(user.getProjects()));
	}
	
	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Project> projects){
		return projects.stream().map(project -> new SimpleGrantedAuthority(project.getProjectName())).collect(Collectors.toList());
	}


}
