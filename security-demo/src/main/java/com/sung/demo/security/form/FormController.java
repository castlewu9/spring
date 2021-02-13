package com.sung.demo.security.form;

import java.security.Principal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FormController {

  @GetMapping("/")
  String index(Model model, Principal principal) {
    if (principal == null) {
      model.addAttribute("message", "Hello Spring Security");
    } else {
      model.addAttribute("message", "Hello " + principal.getName());
    }
    return "index";
  }

  @GetMapping("/info")
  String info(Model model, Principal principal) {
    if (principal == null) {
      model.addAttribute("message", "Hello Spring Security");
    } else {
      model.addAttribute("message", "Hello " + principal.getName());
    }
    return "info";
  }

  @GetMapping("/dashboard")
  String dashboard(Model model, Principal principal) {
    model.addAttribute("message", "Hello " + principal.getName());
    return "dashboard";
  }

  @GetMapping("/admin")
  String admin(Model model, Principal principal) {
    model.addAttribute("message", "Hello " + principal.getName());
    return "admin";
  }
  
  @GetMapping("/user")
  String user(Model model, Principal principal) {
    model.addAttribute("message", "Hello " + principal.getName());
    return "user";
  }

}
