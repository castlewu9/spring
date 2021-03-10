package com.sung.demo.jpa.user;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressId implements Serializable {

  private static final long serialVersionUID = -240659356121636519L;

  private String username;

  private String zip;

}
