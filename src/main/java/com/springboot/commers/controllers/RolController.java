package com.springboot.commers.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.springboot.commers.validators.RolValidator;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200", originPatterns = "*")
@RestController
@RequestMapping("/api/rol")
public class RolController {
    private final IRolService service;
    private final RolValidator rolValidator;

    // @Autowired
    public RolController(IRolService service, RolValidator rolValidator) {
        this.service = service;
        this.rolValidator = rolValidator;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")    public List<Rol> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
      @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> view(@PathVariable Long id) {
        Optional<Rol> rolOptional = service.findById(id);
        if (rolOptional.isPresent()) {
            return ResponseEntity.ok(rolOptional.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody Rol rol, BindingResult result) {
        rolValidator.validate(rol, result);
        if (result.hasErrors()) {
            return validation(result);
        }

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(rol));
        } catch (DataIntegrityViolationException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No se puede repetir el nombre del rol.");

        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: Violación de integridad de datos.");

        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> update(@Valid @RequestBody Rol rol, BindingResult result, @PathVariable Long id) {
        rolValidator.validate(rol, result);
        if (result.hasErrors()) {
            return validation(result);
        }

        Optional<Rol> rolOptional = service.update(id, rol);
        if (rolOptional.isPresent()) {
            return ResponseEntity.ok().body(rolOptional.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Rol> rolOptional = service.delete(id);
        if (rolOptional.isPresent()) {
            return ResponseEntity.ok(rolOptional.orElseThrow());
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
