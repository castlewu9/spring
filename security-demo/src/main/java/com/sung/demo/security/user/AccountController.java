package com.sung.demo.security.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

  AccountRepository accountRepository;

  PasswordEncoder passwordEncoder;

  public AccountController(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
    this.accountRepository = accountRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping("/account")
  Account addNew(@RequestParam("name") String name, @RequestParam("role") String role) {
    Account account = Account.builder().username(name).password(passwordEncoder.encode("password"))
        .role(role).build();
    return accountRepository.save(account);
  }

}
