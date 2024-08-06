package com.springboot.commers.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.commers.entities.Employees;
import com.springboot.commers.entities.Product;
import com.springboot.commers.services.IEmployeeService;
import com.springboot.commers.services.IProductsService;
import com.springboot.commers.validators.ProductValidator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "http://localhost:4200", originPatterns = "*")
@RestController
@RequestMapping("/api/product")
public class ProductsController {

    private final IProductsService service;
    private final IEmployeeService serviceEmployee;
    private final ProductValidator productValidator;

    @Autowired
    public ProductsController(IProductsService service, IEmployeeService serviceEmployee, ProductValidator productValidator) {
        this.service = service;
        this.serviceEmployee = serviceEmployee;
        this.productValidator = productValidator;
    }

    @GetMapping
    // @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<Product> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    // @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> view(@PathVariable Long id) {
        Optional<Product> productOptional = service.findById(id);
        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody Product product, BindingResult result) {
        productValidator.validate(product, result);
        if (result.hasErrors()) {
            return validation(result);
        }

        // Aquí se debe reemplazar la lógica para obtener el empleado actual
        Employees employee = serviceEmployee.findById(1L).orElseThrow();

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(product, employee));
    }

    @PutMapping("/{id}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@Valid @RequestBody Product product, BindingResult result, @PathVariable Long id) {
        productValidator.validate(product, result);
        if (result.hasErrors()) {
            return validation(result);
        }

        // Aquí se debe reemplazar la lógica para obtener el empleado actual
        Employees employee = serviceEmployee.findById(2L).orElseThrow();

        Optional<Product> produOptional = service.update(id, product, employee);
        if (produOptional.isPresent()) {
            return ResponseEntity.ok(produOptional.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Product> productOptional = service.delete(id);
        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.orElseThrow());
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
