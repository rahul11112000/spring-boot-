package com.example.digitalwallet.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.digitalwallet.entity.Users;
import com.example.digitalwallet.repository.UserRepository;

@Service
public class UserService {

   @Autowired
   UserRepository userRepository;
   
   public Optional<Users> getUser(Integer id){

      return userRepository.findById(id);

   }

   public Optional<Users> getUserByEmail(String email){

      return userRepository.findByEmail(email);

   }
}
