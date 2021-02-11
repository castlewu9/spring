package com.sung.demo.security.user;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AccountService implements UserDetailsService {

  private AccountRepository accountRepositorty;

  public AccountService(AccountRepository accountRepositorty) {
    this.accountRepositorty = accountRepositorty;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Account account = accountRepositorty.findByUsername(username);
    if (account == null) {
      throw new UsernameNotFoundException(username);
    }
    return User.builder().username(account.getUsername()).password(account.getPassword())
        .roles(account.getRole()).build();
  }

}
