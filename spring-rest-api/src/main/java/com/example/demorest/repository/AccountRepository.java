package com.example.demorest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demorest.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
	
	Optional<Account> findByEmail(String username);

}
