package com.springboot.commers.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.springboot.commers.entities.Employee;
import com.springboot.commers.entities.Invoice;
import com.springboot.commers.services.IEmployeeService;
import com.springboot.commers.services.IInvoiceService;


import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/invoices")
@CrossOrigin(origins = "http://localhost:4200", originPatterns = "*")
public class InvoiceController {

    @Autowired
    private IInvoiceService invoiceService;

    @Autowired
    private IEmployeeService employeeService;


    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<Invoice> list() {
        return invoiceService.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<?> view(@PathVariable Long id) {
        Optional<Invoice> invoiceOptional = invoiceService.findById(id);
        if (invoiceOptional.isPresent()) {
            return ResponseEntity.ok(invoiceOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody Invoice invoice, BindingResult result) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();

        Employee employee = employeeService.findByName(name).orElseThrow(() -> new RuntimeException("Employee not found"));
        invoice.setEmployee(employee);
        invoice.setDate(new java.util.Date());

        // Save lines and calculate total
        double total = invoice.getLinesInvoice().stream()
            .mapToDouble(line -> {
                line.setInvoice(invoice);
                return line.getAmount() * line.getProduct().getPrice();
            })
            .sum();

        invoice.setWhole(total);

        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceService.save(invoice));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@Valid @RequestBody Invoice invoice, BindingResult result, @PathVariable Long id ) {
        if (result.hasFieldErrors()) {
            return validation(result);
        }

        Optional<Invoice> invoiceOptional = invoiceService.findById(id);
        if (!invoiceOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Invoice currentInvoice = invoiceOptional.get();
        currentInvoice.setDate(new java.util.Date());
        currentInvoice.setLinesInvoice(invoice.getLinesInvoice());

        // Save lines and calculate total
        double total = invoice.getLinesInvoice().stream()
            .mapToDouble(line -> {
                line.setInvoice(currentInvoice);
                return line.getAmount() * line.getProduct().getPrice();
            })
            .sum();

        currentInvoice.setWhole(total);

        return ResponseEntity.status(HttpStatus.CREATED).body(invoiceService.save(currentInvoice));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Invoice> invoiceOptional = invoiceService.findById(id);
        if (!invoiceOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        invoiceService.delete(id);
        return ResponseEntity.ok().build();
    }

    private ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = result.getFieldErrors().stream()
            .collect(Collectors.toMap(
                err -> err.getField(),
                err -> "El campo " + err.getField() + " " + err.getDefaultMessage()
            ));
        return ResponseEntity.badRequest().body(errors);
    }
}