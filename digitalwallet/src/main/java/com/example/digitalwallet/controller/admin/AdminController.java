package com.example.digitalwallet.controller.admin;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/admin")
public class AdminController {
   
   @GetMapping("/")
   public String checkAdmin() {
       return "admin login";
   }
   
}
