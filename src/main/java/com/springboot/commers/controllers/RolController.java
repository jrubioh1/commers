package com.springboot.commers.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.springboot.commers.entities.Rol;
import com.springboot.commers.services.IRolService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200", originPatterns = "*")
@RestController
@RequestMapping("/api/rol")
public class RolController {
    private final IRolService service;

    //@Autowired
    public RolController(IRolService service) {
        this.service = service;
    }

    @GetMapping
    // @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<Rol> list() {
        return service.findAll();

    }

    @GetMapping("/{id}")
    // @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> view(@PathVariable Long id) {

        Optional<Rol> productOptional = service.findById(id);
        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create( @RequestBody Rol rol) {
    
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(rol));
    }

    @PutMapping("/{id}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@Valid @RequestBody Rol rol, BindingResult result, @PathVariable Long id) {
   
       

        Optional<Rol> produOptional = service.update(id, rol);
        if (produOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(produOptional.orElseThrow());

        }

        return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {

        Optional<Rol> productOptional = service.delete(id);
        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

   
}


    


