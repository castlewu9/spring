package com.sung.demo.restapi.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.sung.demo.restapi.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

  Optional<Account> findByEmail(String username);

}
