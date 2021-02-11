package com.sung.demo.security.user;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {

  @Id
  @Builder.Default
  private String id = UUID.randomUUID().toString();

  @Column(unique = true)
  private String username;

  private String password;

  private String role;

}
