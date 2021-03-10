package com.sung.demo.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import com.sung.demo.jpa.user.User;
import com.sung.demo.jpa.user.UserRepository;

@Component
public class DemoRunner implements ApplicationRunner {

  @Autowired
  UserRepository userRepository;

  @Override
  public void run(ApplicationArguments args) throws Exception {
    // User user = new User();
    // user.setUsername("sung");
    // user.setPassword("123");
    // user.addAddress(new Address("sung", "001", "street", "city", "state"));
    // user.addAddress(new Address("sung", "002", "street", "Seoul", "Seoul"));
    // userRepository.save(user);
    User user = userRepository.findByUsernameAndAddressesZip("sung", "001");
    System.out.println(user);
  }

}
