package com.intractiv.userapi.service;

import com.intractiv.userapi.dto.UserDTO;
import com.intractiv.userapi.model.User;
import com.intractiv.userapi.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("UserService")
public class UserServiceImpl implements UserService{

   private final UserRepository userRepository;
   private final BCryptPasswordEncoder bCryptPasswordEncoder;
   private final ModelMapper mapper = new ModelMapper();

   public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
      this.userRepository = userRepository;
      this.bCryptPasswordEncoder = bCryptPasswordEncoder;
   }

   @Override
   public UserDTO entityToDTO(User user) {
      if( user == null) {
         return null;
      } else {
         return mapper.map(user, UserDTO.class);
      }
   }

   @Override
   public User dtoToEntity(UserDTO userDTO) {
      if( userDTO == null ) {
         return null;
      } else {
         return mapper.map(userDTO, User.class);
      }
   }

   @Override
   public Boolean existsByName(String name) {
      return userRepository.existsByName(name);
   }

   @Override
   public UserDTO findByName(String name) {
      User user = userRepository.findByName(name);
      return entityToDTO(user);
   }

   @Override
   public UserDTO save(UserDTO userDTO) {
      User user = dtoToEntity(userDTO);
      user = userRepository.save(user);
      return entityToDTO(user);
   }

   @Override
   public String encryptPassword(String rawPassword) {
      return bCryptPasswordEncoder.encode(rawPassword);
   }

   @Override
   public Boolean matches(String rawPassword, String password) {
      return bCryptPasswordEncoder.matches(rawPassword, password);
   }
}
