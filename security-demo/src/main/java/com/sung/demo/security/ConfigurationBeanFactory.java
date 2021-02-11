package com.sung.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.sung.demo.security.user.AccountRepository;
import com.sung.demo.security.user.AccountService;

@Configuration
public class ConfigurationBeanFactory {

  @Bean
  AccountService defaultUserDetailService(AccountRepository accountRepository) {
    return new AccountService(accountRepository);
  }

  @Bean
  PasswordEncoder defaultPasswordEncoder() {
    return PasswordEncoderFactories.createDelegatingPasswordEncoder();
  }

}
