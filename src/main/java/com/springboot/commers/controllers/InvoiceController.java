package com.springboot.commers.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

import com.springboot.commers.entities.Employees;
import com.springboot.commers.entities.Invoice;
import com.springboot.commers.services.IInvoiceService;
import com.springboot.commers.services.IUserService;
import com.springboot.commers.validators.InvoiceValidator;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200", originPatterns = "*")
@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final IInvoiceService service;
    private final IUserService serviceUser; 
    private final InvoiceValidator invoiceValidator;

    // @Autowired
    public InvoiceController(IInvoiceService service, InvoiceValidator invoiceValidator, IUserService serviceUser) {
        this.service = service;
        this.invoiceValidator = invoiceValidator;
        this.serviceUser= serviceUser;
    }

    @GetMapping
    // @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<Invoice> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    public ResponseEntity<?> view(@PathVariable Long id) {
        Optional<Invoice> invoiceOptional = service.findById(id);
        if (invoiceOptional.isPresent()) {
            return ResponseEntity.ok(invoiceOptional.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    public ResponseEntity<?> create(@Valid @RequestBody Invoice invoice, BindingResult result) {
        invoiceValidator.validate(invoice, result);
        if (result.hasErrors()) {
            return validation(result);
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employees employeeDB= serviceUser.findEmployeeByEmail(authentication.getName()).orElseThrow();
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(invoice, employeeDB));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    public ResponseEntity<?> update(@Valid @RequestBody Invoice invoice, BindingResult result, @PathVariable Long id) {
        invoiceValidator.validate(invoice, result);
        if (result.hasErrors()) {
            return validation(result);
        }
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Employees employeeDB= serviceUser.findEmployeeByEmail(authentication.getName()).orElseThrow();
        Optional<Invoice> invoiceOptional = service.update(id, invoice, employeeDB);
        if (invoiceOptional.isPresent()) {
            return ResponseEntity.ok(invoiceOptional.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Invoice> invoiceOptional = service.delete(id);
        if (invoiceOptional.isPresent()) {
            return ResponseEntity.ok(invoiceOptional.orElseThrow());
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
