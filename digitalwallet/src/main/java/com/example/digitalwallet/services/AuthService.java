package com.example.digitalwallet.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.digitalwallet.entity.Users;
import com.example.digitalwallet.repository.UserRepository;

@Service
public class AuthService {

   @Autowired
   UserRepository userRepository;

   public String passwordEncode(String password){
      BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
      return encoder.encode(password);
   }

   public String saveUser(@RequestBody Users user){

      String password = passwordEncode(user.getPassword());

      Users u = new Users();
      u.setName(user.getName());
      u.setEmail(user.getEmail());
      u.setRole(user.getRole());
      u.setPassword(password);

      userRepository.save(u); 
      return "User Save successfully";
   }

}
