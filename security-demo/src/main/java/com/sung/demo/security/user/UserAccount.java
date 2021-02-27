package com.sung.demo.security.user;

import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import lombok.Getter;

@Getter
public class UserAccount extends User {

  /**
   * 
   */
  private static final long serialVersionUID = 1394831764004392587L;

  private Account account;

  public UserAccount(Account account) {
    super(account.getUsername(), account.getPassword(),
        List.of(new SimpleGrantedAuthority("ROLE_" + account.getRole())));
    this.account = account;
  }

}
