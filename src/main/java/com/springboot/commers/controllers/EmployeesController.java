package com.springboot.commers.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import com.springboot.commers.entities.Employees;
import com.springboot.commers.services.interfaces.IEmployeeService;
import com.springboot.commers.validations.OnCreate;
import com.springboot.commers.validators.UserValidator;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200", originPatterns = "*")
@RestController
@RequestMapping("/api/employees")
public class EmployeesController {

    private final IEmployeeService service;
    private final UserValidator userValidator;

     @Autowired
    private PasswordEncoder passwordEncoder;

   //@Autowired
    public EmployeesController(IEmployeeService service, UserValidator userValidator) {
        this.service = service;
        this.userValidator = userValidator;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<Employees> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> view(@PathVariable Long id) {
        Optional<Employees> optionalEmployees = service.findById(id);
        if (optionalEmployees.isPresent()) {
            return ResponseEntity.ok(optionalEmployees.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> create(@Validated(OnCreate.class) @RequestBody Employees employee, BindingResult result) {
        userValidator.validate(employee, result);
        if (result.hasErrors()) {
            return validation(result);
        }
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(employee));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> update(@Valid @RequestBody Employees employee, BindingResult result, @PathVariable Long id) {
        userValidator.validate(employee, result);
        if (result.hasErrors()) {
            return validation(result);
        }

        Optional<Employees> optionalEmployee = service.update(id, employee);
        if (optionalEmployee.isPresent()) {
            return ResponseEntity.ok(optionalEmployee.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Employees> optionalEmployee = service.delete(id);
        if (optionalEmployee.isPresent()) {
            return ResponseEntity.ok(optionalEmployee.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/toggle-status/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> switchStatusEmployee(@PathVariable Long id) {
        Optional<Employees> optionalEmployee = service.findById(id);
        if (optionalEmployee.isPresent()) {
            Employees employee = optionalEmployee.get();
            employee.setEnabled(!employee.isEnabled());
            return ResponseEntity.ok(service.update(id, employee));
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
