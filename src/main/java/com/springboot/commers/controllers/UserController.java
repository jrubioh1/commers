package com.springboot.commers.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.commers.entities.User;
import com.springboot.commers.services.interfaces.IUserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private IUserService service;

  @GetMapping("/email/{email}")
  @PreAuthorize("hasAnyRole('EMPLOYEE')")
  public ResponseEntity<?> email(@PathVariable String email) {
    Optional<User> userOptional = service.findByEmail(email);
    if (userOptional.isPresent()) {
      return ResponseEntity.ok().body(userOptional.get());
    }

    return ResponseEntity.notFound().build();
  }
}
