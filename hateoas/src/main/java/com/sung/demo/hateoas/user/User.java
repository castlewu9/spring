package com.sung.demo.hateoas.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.hateoas.RepresentationModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "Users")
public class User extends RepresentationModel<User> {

  @Id
  private String id;

  private String fname;
  private String lname;
  private String status;
  private String role;

  @JsonProperty("admin")
  private boolean isAdmin() {
    return role.equalsIgnoreCase("admin");
  }

}
