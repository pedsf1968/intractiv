package com.intractiv.userapi.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity(name = "User")
@Table(name = "users")
public class User {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   @Column(name = "id")
   private Integer id;

   @NotNull
   @Column(name = "name")
   private String name;

   @NotNull
   @Column(name = "password")
   private String password;

   @Column(name = "phone")
   private String phone;
}
