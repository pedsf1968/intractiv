package com.intractiv.userapi.dto;

import lombok.Data;

@Data
// Data Transfert Object for Controller
public class UserDTO {

   private Integer id;
   private String name;
   private String password;
   private String phone;
}
