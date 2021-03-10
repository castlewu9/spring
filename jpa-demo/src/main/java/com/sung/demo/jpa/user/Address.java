package com.sung.demo.jpa.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "addresses")
@IdClass(AddressId.class)
public class Address {

  @Id
  private String username;

  @Id
  private String zip;

  private String street;

  private String city;

  private String state;

}
