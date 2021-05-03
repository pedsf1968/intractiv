package com.intractiv.userapi.controller;


import com.intractiv.userapi.dto.UserDTO;
import com.intractiv.userapi.model.Compliance;
import com.intractiv.userapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/")
public class UserController {

   private final UserService userService;

   public UserController(UserService userService) {
      this.userService = userService;
   }

   @GetMapping(value = "/api/compliance/password/{password}",
         produces = MediaType.APPLICATION_JSON_VALUE)
   public  ResponseEntity<Compliance> passwordChek(@RequestParam @PathVariable String password) {

      // Compliance test if string is compliant
      Compliance response = new Compliance(password);

      // should respond ok only if compliant?
      return ResponseEntity.ok(response);
   }

   @PostMapping(value = "/api/user/",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {

      userDTO.setValid(false);

      if( userDTO == null) {
         userDTO.setReason(Compliance.ERROR_USER_NULL);
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
      } else if(Boolean.TRUE.equals(userService.existsByName(userDTO.getName()))) {
         userDTO.setReason(Compliance.ERROR_USER_EXISTS);
         return ResponseEntity.status(HttpStatus.CONFLICT).build();
      } else {
         userDTO.isValid(userDTO.getPassword());
         if(Boolean.TRUE.equals(userDTO.isValid())) {
            // encrypt password
            userDTO.setPassword(userService.encryptPassword(userDTO.getPassword()));
            userDTO = userService.save(userDTO);
            userDTO.setValid(true);
            userDTO.setReason(Compliance.MESSAGE_USER_CREATED);
            return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
         } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userDTO);
         }

      }
   }

   @GetMapping(value = "/api/user/{login}",
      produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<UserDTO> findUserByName(@PathVariable("login") String login) {

      UserDTO userDTO = new UserDTO();
      userDTO.setValid(false);

      if( login == null || login.isEmpty()) {
         userDTO.setReason(Compliance.ERROR_USER_NULL);
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(userDTO);
      } else {
         userDTO = userService.findByName(login);
         if( userDTO != null) {
            userDTO.setValid(true);
            userDTO.setReason(userDTO.MESSAGE_USER_FOUND);
            return ResponseEntity.status(HttpStatus.OK).body(userDTO);
         } else {
            userDTO = new UserDTO();
            userDTO.setReason(userDTO.ERROR_USER_NOTFOUND);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
         }
      }
   }

   @PostMapping(value = "/api/user/{login}/verify",
         consumes = MediaType.APPLICATION_JSON_VALUE,
         produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<Compliance> verifyPassword(@PathVariable("login") String login, @RequestBody String password){
      Compliance response;
      UserDTO userDTO = userService.findByName(login);

      if (userDTO == null) {
         response = new Compliance(false, Compliance.ERROR_USER_NOTFOUND);
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
      } else if( userService.matches(password, userDTO.getPassword())) {
         response = new Compliance(true, Compliance.MESSAGE_PASSWORD_OK);
         return ResponseEntity.status(HttpStatus.OK).body(response);
      } else {
         response = new Compliance(false, Compliance.ERROR_INVALID);
         return ResponseEntity.status(HttpStatus.FOUND).body(response);
      }
   }

}
