package com.intractiv.userapi.dto;

import com.intractiv.userapi.model.Compliance;
import lombok.Data;

@Data
// Data Transfert Object for Controller
public class UserDTO  extends Compliance {

   private Integer id;
   private String name;
   private String password;
   private String phone;
}
