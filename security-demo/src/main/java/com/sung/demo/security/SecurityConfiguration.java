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
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.context.request.async.WebAsyncManagerIntegrationFilter;
import com.sung.demo.security.logger.LoggingFilter;
import com.sung.demo.security.user.AccountService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private AccountService accountService;

  private AccessDeniedHandler accessDeniedHandler;

  public SecurityConfiguration(AccountService accountService,
      AccessDeniedHandler accessDeniedHandler) {
    this.accountService = accountService;
    this.accessDeniedHandler = accessDeniedHandler;
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
    http.addFilterBefore(new LoggingFilter(), WebAsyncManagerIntegrationFilter.class);

    http.authorizeRequests().mvcMatchers("/", "/info", "/account/**", "/signup").permitAll()
        .mvcMatchers("/admin").hasRole("ADMIN").mvcMatchers("/user").hasRole("USER").anyRequest()
        .authenticated().accessDecisionManager(accessDecisionManager());

    http.formLogin().loginPage("/login").permitAll();
    http.logout().logoutSuccessUrl("/");
    http.httpBasic();

    http.rememberMe().key("remember-me-key");

    http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(accountService);
  }

}
