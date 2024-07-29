package com.springboot.commers.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.commers.entities.Client;
import com.springboot.commers.services.IClientService;



@CrossOrigin(origins = "http://localhost:4200", originPatterns = "*")
@RestController
@RequestMapping("/api/clients")
public class ClientesController {
   
    
    @Autowired
    private IClientService service;

    @GetMapping
    public List<Client> list() {
        return service.findAll();
    }

    @PostMapping
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody Client client) {
       
 
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(client));}
  

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Client client) {
    
        return create(client);
     }



}
