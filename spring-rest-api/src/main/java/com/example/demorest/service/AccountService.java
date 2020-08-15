package com.example.demorest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demorest.domain.Account;
import com.example.demorest.domain.AccountAdapter;
import com.example.demorest.repository.AccountRepository;

@Service
public class AccountService implements UserDetailsService {
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public Account saveAccount(Account account) {
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		return accountRepository.save(account);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException(username));
//		return new AccountAdapter(account.getEmail(), account.getPassword(), authorities(account.getRoles()));
		return new AccountAdapter(account);
	}

//	private Collection<? extends GrantedAuthority> authorities(Set<AccountRole> roles) {
//		return roles.stream()
//				.map(r -> new SimpleGrantedAuthority("ROLE" + r.name()))
//				.collect(Collectors.toSet());
//	}

}
