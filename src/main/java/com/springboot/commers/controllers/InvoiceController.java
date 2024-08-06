package com.springboot.commers.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.springboot.commers.entities.Employees;
import com.springboot.commers.entities.Invoice;
import com.springboot.commers.services.IEmployeeService;
import com.springboot.commers.services.IInvoiceService;
import com.springboot.commers.validators.InvoiceValidator;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200", originPatterns = "*")
@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final IInvoiceService service;
    private final IEmployeeService serviceEm;
    private final InvoiceValidator invoiceValidator;

    @Autowired
    public InvoiceController(IInvoiceService service, IEmployeeService serviceEm, InvoiceValidator invoiceValidator) {
        this.service = service;
        this.serviceEm = serviceEm;
        this.invoiceValidator = invoiceValidator;
    }

    @GetMapping
    // @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<Invoice> list() {
        return service.findAll();
    }

    @PostMapping
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody Invoice invoice, BindingResult result) {
        invoiceValidator.validate(invoice, result);
        if (result.hasErrors()) {
            return validation(result);
        }

        // Aquí se debe reemplazar la lógica para obtener el empleado actual
        Employees employee = serviceEm.findById(1L).orElseThrow();

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(invoice, employee));
    }

    @PutMapping("/{id}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@Valid @RequestBody Invoice invoice, BindingResult result, @PathVariable Long id) {
        invoiceValidator.validate(invoice, result);
        if (result.hasErrors()) {
            return validation(result);
        }

        // Aquí se debe reemplazar la lógica para obtener el empleado actual
        Employees employee = serviceEm.findById(2L).orElseThrow();

        Optional<Invoice> invoiceOptional = service.update(id, invoice, employee);
        if (invoiceOptional.isPresent()) {
            return ResponseEntity.ok(invoiceOptional.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    // @PreAuthorize("hasRole('ADMIN')")
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
