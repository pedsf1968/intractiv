package com.intractiv.userapi.service;

import com.intractiv.userapi.dto.UserDTO;
import com.intractiv.userapi.model.User;

public interface UserService {

   UserDTO entityToDTO(User user);
   User dtoToEntity(UserDTO userDTO);

   Boolean existsByName(String name);
   UserDTO findByName(String name);

   UserDTO save(UserDTO userDTO);

   String encryptPassword(String password);
}
