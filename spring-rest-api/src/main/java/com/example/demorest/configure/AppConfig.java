package com.example.demorest.configure;

import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demorest.domain.Account;
import com.example.demorest.domain.AccountRole;
import com.example.demorest.service.AccountService;

@Configuration
public class AppConfig {
	
	@Autowired
	AppProperties appProperties;
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	@Bean
	public ApplicationRunner applicationRunner() {
		return new ApplicationRunner() {
			
			@Autowired
			AccountService accountService;

			@Override
			public void run(ApplicationArguments args) throws Exception {
				Account admin = Account.builder()
						.email(appProperties.getAdminName())
						.password(appProperties.getAdminPassword())
						.roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
						.build();
				accountService.saveAccount(admin);
				
				Account user = Account.builder()
						.email(appProperties.getUserName())
						.password(appProperties.getUserPassword())
						.roles(Set.of(AccountRole.ADMIN, AccountRole.USER))
						.build();
				accountService.saveAccount(user);
			}
		};
	}

}
