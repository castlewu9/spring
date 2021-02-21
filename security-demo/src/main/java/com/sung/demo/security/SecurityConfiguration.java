package com.sung.demo.security;

import java.util.Arrays;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import com.sung.demo.security.user.AccountService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private AccountService accountService;

  public SecurityConfiguration(AccountService accountService) {
    this.accountService = accountService;
  }

  public AccessDecisionManager accessDecisionManager() {
    RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
    roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

    DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
    handler.setRoleHierarchy(roleHierarchy);


    WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
    webExpressionVoter.setExpressionHandler(handler);

    List<AccessDecisionVoter<? extends Object>> voters = Arrays.asList(webExpressionVoter);
    return new AffirmativeBased(voters);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.authorizeRequests().mvcMatchers("/", "/info", "/account/**", "/signup").permitAll()
        .mvcMatchers("/admin").hasRole("ADMIN").mvcMatchers("/user").hasRole("USER").anyRequest()
        .authenticated().accessDecisionManager(accessDecisionManager());

    http.formLogin();
    http.httpBasic();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(accountService);
  }

}
