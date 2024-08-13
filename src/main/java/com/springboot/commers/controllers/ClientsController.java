package com.springboot.commers.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.commers.entities.Clients;
import com.springboot.commers.services.IClientService;
import com.springboot.commers.validations.OnCreate;
import com.springboot.commers.validators.UserValidator;


import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200", originPatterns = "*")
@RestController
@RequestMapping("/api/clients")
public class ClientsController {

    private final IClientService service;

    private final UserValidator userValidator;

   // @Autowired
    public ClientsController(IClientService service, UserValidator userValidator) {
        this.service = service;
        this.userValidator = userValidator;
    }
 
    @GetMapping
    public List<Clients> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Long id) {
        Optional<Clients> clientsOptional = service.findById(id);
        if (clientsOptional.isPresent()) {
            return ResponseEntity.ok(clientsOptional.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

 
    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody Clients client, BindingResult result) {
        userValidator.validate(client, result);
        if (result.hasErrors()) {
            return validation(result);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(client));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated(OnCreate.class)  @RequestBody Clients client, BindingResult result) {
        userValidator.validate(client, result);
        if (result.hasErrors()) {
            return validation(result);
        }
        return create(client, result);
    }
 
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Clients client, BindingResult result, @PathVariable Long id) {
        userValidator.validate(client, result);
        if (result.hasErrors()) {
            return validation(result);
        }

        Optional<Clients> optionalClients = service.update(id, client);
        if (optionalClients.isPresent()) {
            return ResponseEntity.ok(optionalClients.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Clients> optionalClients = service.delete(id);
        if (optionalClients.isPresent()) {
            return ResponseEntity.ok(optionalClients.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();

        result.getFieldErrors().forEach(err -> {
            errors.put(err.getField(), "El campo " + err.getField() + " " + err.getDefaultMessage());
        });

        return ResponseEntity.badRequest().body(errors);
    }
}
