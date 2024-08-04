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

import com.springboot.commers.entities.Employees;
import com.springboot.commers.entities.Invoice;
import com.springboot.commers.entities.Product;
import com.springboot.commers.services.IEmployeeService;
import com.springboot.commers.services.IInvoiceService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200", originPatterns = "*")
@RestController
@RequestMapping("/invoices")
public class InvoiceController {

    private final IInvoiceService service;

    private final IEmployeeService serviceEm;

    //@Autowired
    public InvoiceController(IInvoiceService service) {
        this.service = service;
    }

  

    @GetMapping
    // @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<Invoice> list() {
        return service.findAll();

    }

       @PostMapping
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create( @RequestBody Invoice invoice) {
          // validation.validate(product, result);
      

       /*  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();

        Employees employee = serviceEmployee.findByName(name).orElseThrow();
        if (employee == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Employee not found.");
        } */
        Employees employee = serviceEm.findById(1L).orElseThrow();
     
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(invoice, employee));
    }

    @PutMapping("/{id}")
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@Valid @RequestBody Invoice invoice, BindingResult result, @PathVariable Long id) {
        /*
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();

        Employees employee = serviceEmployee.findByName(name).orElseThrow();
        if (employee == null) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Employee not found.");
        }*/

        Employees employee= serviceEm.findById(2L).orElseThrow();

        Optional<Invoice> invoiceOptional = service.update(id, invoice, employee);
        if (invoiceOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(invoiceOptional.orElseThrow());

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











}
