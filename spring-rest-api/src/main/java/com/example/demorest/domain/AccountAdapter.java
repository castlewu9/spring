package com.example.demorest.domain;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class AccountAdapter extends User {
	
	public Account account;
	
	public AccountAdapter(Account account) {
		super(account.getEmail(), account.getPassword(), authorities(account.getRoles()));
		this.account = account;
	}

	private static Collection<? extends GrantedAuthority> authorities(Set<AccountRole> roles) {
		return roles.stream()
			.map(r -> new SimpleGrantedAuthority("ROLE" + r.name()))
			.collect(Collectors.toSet());
	}
	
	Account getAccount() {
		return this.account;
	}

}
