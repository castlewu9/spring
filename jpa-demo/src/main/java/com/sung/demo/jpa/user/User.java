package com.sung.demo.jpa.user;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

  @Id
  @GeneratedValue
  private Integer id;

  @Column(nullable = false, unique = true)
  private String username;

  private String password;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private Set<Address> addresses = new HashSet<Address>();

  public void addAddress(Address address) {
    addresses.add(address);
  }

}
