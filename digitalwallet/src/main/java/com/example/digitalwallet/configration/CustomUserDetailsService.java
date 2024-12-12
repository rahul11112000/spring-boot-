package com.example.digitalwallet.configration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.digitalwallet.entity.Users;
import com.example.digitalwallet.repository.UserRepository;


@Service
public class CustomUserDetailsService implements UserDetailsService {

   @Autowired
   UserRepository userRepository;

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      // Fetch the user from the database
      Users user = userRepository.findByEmail(username)
               .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

      // Map to Spring Security's UserDetails
      return org.springframework.security.core.userdetails.User
               .withUsername(user.getEmail())
               .password(user.getPassword()) 
               .authorities(user.getRole())
               .build();
   }
}