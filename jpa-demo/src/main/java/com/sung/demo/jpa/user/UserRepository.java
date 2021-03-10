package com.sung.demo.jpa.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

  User findByUsernameAndAddressesZip(String username, String zip);

}
