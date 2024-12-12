package com.example.digitalwallet.controller.client;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.digitalwallet.dto.Transfer;
import com.example.digitalwallet.entity.Users;
import com.example.digitalwallet.entity.Wallet;
import com.example.digitalwallet.services.UserService;
import com.example.digitalwallet.services.WalletService;

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/client")
public class ClientController {

   @Autowired
   UserService userService;

   @Autowired
   WalletService walletService;
   
   @GetMapping("/")
   public String check() {
       return "client login";
   }

   @GetMapping("/user/details")
   public Optional<Users> getDetails(Authentication authentication){

      return userService.getUserByEmail(authentication.getName());

   }

   @GetMapping("/wallet/{userId}")
   public Optional<Wallet> getWalletDetails(@PathVariable Integer userId){

      Optional<Users> user = userService.getUser(userId);
      Users userObj = user.get();

      return walletService.getDetails(userObj);
      
   }

   @PostMapping("/create/wallet")
   public String createWallet(Authentication authentication){

      return walletService.createWallet(authentication);

   }

   @PutMapping("/add/money")
   public String addMoneyInWallet(Authentication authentication,@RequestBody Map<String, Double> request){

      Double amount = request.get("amount");

      if (amount == null || amount <= 0) {
        return "Invalid amount provided.";
      }

      return walletService.addMoney(authentication, amount);
      
   }

   @PutMapping("use/money")
   public String useMoney(Authentication authentication,@RequestBody Map<String, Double> request){

      Double amount = request.get("amount");
      if (amount == null || amount <= 0) {
         return "Invalid amount provided.";
      }
      return walletService.useMoney(authentication, amount);
   }

   @PutMapping("/transfer/money")
   public String transferMoney(Authentication authentication,@RequestBody Transfer transfer){
      
      return walletService.transfer(authentication, transfer);
      
   }
   
}
