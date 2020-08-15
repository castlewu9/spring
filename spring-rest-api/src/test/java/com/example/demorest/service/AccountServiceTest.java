package com.example.demorest.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.demorest.domain.Account;
import com.example.demorest.domain.AccountRole;
import com.example.demorest.repository.AccountRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class AccountServiceTest {

	@Autowired
	AccountService accountService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	AccountRepository accountRepository;
	
	@BeforeEach
	public void setup() {
		accountRepository.deleteAll();
	}

	@Test
	void findByUsername() {
		Account account = Account.builder()
				.email("sungwoo@email.com")
				.password("pass")
				.roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
				.build();
		
		accountService.saveAccount(account);

		UserDetailsService userDetailsService = (UserDetailsService)accountService;
		UserDetails userDetails = userDetailsService.loadUserByUsername("sungwoo@email.com");
		
		assertThat(passwordEncoder.matches("pass", userDetails.getPassword())).isTrue();
	}
	
	@Test
	public void findByUsernameFailed() {
		String username = "exception test username";
		Exception exception = assertThrows(UsernameNotFoundException.class,
				() -> accountService.loadUserByUsername(username));
		assertThat(exception.getMessage()).isEqualTo(username);
	}

}
