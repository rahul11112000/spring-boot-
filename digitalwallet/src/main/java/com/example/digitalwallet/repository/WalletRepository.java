package com.example.digitalwallet.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.example.digitalwallet.entity.Users;
import com.example.digitalwallet.entity.Wallet;

public interface WalletRepository extends CrudRepository<Wallet,Integer> {

   Optional<Wallet> findByUser(Users user);
   
}
