package com.example.digitalwallet.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.digitalwallet.dto.Transfer;
import com.example.digitalwallet.entity.Users;
import com.example.digitalwallet.entity.Wallet;
import com.example.digitalwallet.repository.WalletRepository;

@Service
public class WalletService {
   
   @Autowired
   WalletRepository walletRepository;

   @Autowired
   UserService userService;

   public Optional<Wallet> getDetails(Users user){
      
      return walletRepository.findByUser(user);
   }

   public String createWallet(Authentication authentication){
      Optional<Users> user = userService.getUserByEmail(authentication.getName());
      Users userObj = user.get();

      Optional<Wallet> wallet = walletRepository.findByUser(userObj);


      if (wallet.isPresent()) {
         return "Wallet already exists.";
      }

       wallet.orElseGet(() -> {
         Wallet newWallet = new Wallet();
         newWallet.setUser(userObj); // Associate the wallet with the user
         newWallet.setBalance(0.0); // Set initial balance or any default values
         return walletRepository.save(newWallet); // Save and return the new wallet
      });

      

      return "Wallet Created Successfully";
   }

   public String addMoney(Authentication authentication,double money){

      Optional<Users> user = userService.getUserByEmail(authentication.getName());
      Users userObj = user.get();
      Optional<Wallet> wallet = walletRepository.findByUser(userObj);
      Wallet w = wallet.get();
      double prevAmount = w.getBalance();
      double total = prevAmount + money;
      w.setBalance(total);
      walletRepository.save(w);
      return " Amount added Successfully and Total amount in wallet :"+  total + " Rs." ;
   }


   public String useMoney(Authentication authentication,double money){

      Optional<Users> user = userService.getUserByEmail(authentication.getName());
      Users userObj = user.get();
      Optional<Wallet> wallet = walletRepository.findByUser(userObj);
      Wallet w = wallet.get();
      double prevAmount = w.getBalance();
      double total = prevAmount - money;
      w.setBalance(total);
      walletRepository.save(w);
      return " Amount use Successfully and Total amount in wallet :"+  total + " Rs." ;
   }


   public String transfer(Authentication authentication,Transfer transfer){

      Optional<Users> user = userService.getUserByEmail(authentication.getName());
      Users userObj = user.get();
      Optional<Wallet> wallet = walletRepository.findByUser(userObj);
      Wallet w = wallet.get();

      if(transfer.getAmount() > w.getBalance()){
         return "You Dont have sufficent ammount in your wallet";
      }

      Optional<Users> client = userService.getUserByEmail(transfer.getClientemail());
      Users clientObj = client.get();
      Wallet clientWallet = walletRepository.findByUser(clientObj)
      .orElseThrow(() -> new UsernameNotFoundException("User not found: " + clientObj));

      clientWallet.setBalance(clientWallet.getBalance() + transfer.getAmount());
      walletRepository.save(clientWallet);

      w.setBalance(w.getBalance() - transfer.getAmount());
      walletRepository.save(w);

      return "Money Transfer successfully and your curent balance is :" + w.getBalance();
   }
}
