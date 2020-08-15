package com.sung.demo.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsersRepo {

	@Autowired
	UsersRepository usersRepository;
	
	public void clear() {
		usersRepository.deleteAll();
	}
	
	public Users addAdmin() {
		Users user = Users.builder()
				.bems("12345")
				.fname("John")
				.lname("Doe")
				.status("enabled")
				.role("admin")
				.build();

		return usersRepository.save(user);
	}
	
	public Users addUser() {
		Users user = Users.builder()
				.bems("00001")
				.fname("John")
				.lname("Doe")
				.status("enabled")
				.role("user")
				.build();
		
		return usersRepository.save(user);
	}
}
