package com.sung.demo.security.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignUpController {

  AccountRepository accountRepository;

  PasswordEncoder passwordEncoder;

  public SignUpController(AccountRepository accountRepository, PasswordEncoder passwordEncoder) {
    this.accountRepository = accountRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @GetMapping
  public String signForm(Model model) {
    model.addAttribute("account", new Account());
    return "signup";
  }

  @PostMapping
  public String processSignup(@ModelAttribute Account account) {
    account.setRole("USER");
    account.setPassword(passwordEncoder.encode(account.getPassword()));
    accountRepository.save(account);
    return "redirect:/";
  }

}
