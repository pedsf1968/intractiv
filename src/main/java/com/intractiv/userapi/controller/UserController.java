package com.intractiv.userapi.controller;


import com.intractiv.userapi.dto.UserDTO;
import com.intractiv.userapi.model.Compliance;
import com.intractiv.userapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
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
   public  ResponseEntity<Compliance> passwordChek(@PathVariable String password) {

      // Compliance test if string is compliant
      Compliance response = new Compliance(password);

      // should respond ok only if compliant?
      return ResponseEntity.ok(response);
   }

   @PostMapping(value = "/api/user/",
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
      if( userDTO == null) {
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
      } else if(userService.existsByName(userDTO.getName())) {
         return ResponseEntity.status(HttpStatus.CONFLICT).build();
      } else {
         Compliance compliance = new Compliance(userDTO.getPassword());
         if(compliance.isValid()) {
            // encrypt password
            userDTO.setPassword(userService.encryptPassword(userDTO.getPassword()));
            userDTO = userService.save(userDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(userDTO);
         } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
         }

      }
   }

   @GetMapping(value = "/api/user/{login}",
      produces = MediaType.APPLICATION_JSON_VALUE)
   public ResponseEntity<UserDTO> findUserByName(@PathVariable("login") String name) {

      if( name == null || name.isEmpty()) {
         return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
      } else {
         UserDTO userDTO = userService.findByName(name);
         if( userDTO != null) {
            return ResponseEntity.status(HttpStatus.OK).body(userDTO);
         } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
         }
      }
   }

}
