package com.example.digitalwallet.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.digitalwallet.entity.Users;


public interface UserRepository extends CrudRepository<Users,Integer> {

   Optional<Users> findByEmail(String email);

   Optional<Users> findById(Integer id);
}
