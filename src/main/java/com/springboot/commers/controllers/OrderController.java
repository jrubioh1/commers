package com.springboot.commers.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.commers.entities.Clients;
import com.springboot.commers.entities.Orders;
import com.springboot.commers.helpers.UserHelpers;
import com.springboot.commers.services.interfaces.IClientService;
import com.springboot.commers.services.interfaces.IOrdersService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin(origins = "http://localhost:4200", originPatterns = "*")
@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final IOrdersService ordersService;
    private final IClientService clientService;

    @Autowired
    private UserHelpers helpers;

    public OrderController(IOrdersService ordersService, IClientService clientService) {

        this.ordersService = ordersService;
        this.clientService = clientService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    public List<Orders> list() {

        return ordersService.findAll();

    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLIENT', 'EMPLOYEE')")
    public ResponseEntity<?> view(@PathVariable Long id) {
        Optional<Orders> ordersOptional = ordersService.findById(id);
        if (ordersOptional.isPresent()) {
            return ResponseEntity.ok(ordersOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'CLIENT')")
    public ResponseEntity<?> create(@Valid @RequestBody Orders order, BindingResult result) {

        return ResponseEntity.status(HttpStatus.CREATED).body(ordersService.save(order));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    public ResponseEntity<?> update(@Valid @RequestBody Orders order, BindingResult result, @PathVariable Long id) {

        Optional<Orders> orderOptional = ordersService.findById(id);
        if (orderOptional.isPresent()) {
            return ResponseEntity.ok(orderOptional.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Orders> orderOptional = ordersService.delete(id);
        if (orderOptional.isPresent()) {
            return ResponseEntity.ok(orderOptional.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('EMPLOYEE', 'CLIENT')")
    public ResponseEntity<?> ordersByClient(@PathVariable Long id) {

        // Esto es para que cuando sea un cliente, solo pueda consultar sus ordener y en
        // el caso de hacer redireccionamiento en duro salte error de servidor por no
        // coinicidir el id del autenticado con el peticionado y tambien si el usuario
        // autenticado no se encuentra
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (helpers.hasRole(authentication, "CLIENT")) {

            Optional<Clients> clientOptional = clientService.findByEmail(authentication.getName());
            if (clientOptional.isPresent()) {

                if (clientOptional.get().getId() != id) {
                    return ResponseEntity.internalServerError().build();
                }

            }

            return ResponseEntity.internalServerError().build();

        }

        Optional<Clients> clientOptional = clientService.findById(id);

        if (clientOptional.isPresent()) {

            Clients clientDb = clientOptional.get();
            return ResponseEntity.ok().body(ordersService.findByClient(clientDb));

        }

        return ResponseEntity.notFound().build();

    }
}
