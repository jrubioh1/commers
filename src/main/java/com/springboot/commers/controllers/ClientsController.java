package com.springboot.commers.controllers;

import java.util.ArrayList;
import java.util.Arrays;
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

import com.springboot.commers.entities.Clients;
import com.springboot.commers.entities.Invoice;
import com.springboot.commers.services.IClientService;
import com.springboot.commers.services.IRolService;

@CrossOrigin(origins = "http://localhost:4200", originPatterns = "*")
@RestController
@RequestMapping("/api/clients")
public class ClientsController {


    private final IClientService service;

    
    private final IRolService serviceRol;

    
    //@Autowired
    public ClientsController(IClientService service, IRolService serviceRol) {
        this.service = service;
        this.serviceRol = serviceRol;
    }

    @GetMapping
    public List<Clients> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?>  view(@PathVariable Long id){
         Optional<Clients> clientsOptional = service.findById(id);
        if (clientsOptional.isPresent()) {
            return ResponseEntity.ok(clientsOptional.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@RequestBody Clients client) {

        client.setRoles(Arrays.asList(serviceRol.findByName("ROLE_CLIENT").orElseThrow()));
        client.setInvoices(new ArrayList<Invoice>());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(client));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Clients client) {

        client.setRoles(Arrays.asList(serviceRol.findByName("ROLE_CLIENT").orElseThrow()));
        client.setInvoices(new ArrayList<Invoice>());
        return create(client);
    }

    @PutMapping("/{id}")
     // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody Clients client, @PathVariable Long id) {


        Optional<Clients> optionalClients = service.update(id,client);
        if (optionalClients.isPresent()) {
            return ResponseEntity.ok(optionalClients.orElseThrow());

        }
        return ResponseEntity.notFound().build();
    }

    // hay que implementar un controller para que el propio usuario pueda modificar, pero solo sus datos ""
    // hay que implementar un controller para que el propio usuario pueda eliminarse, pero solo sus datos ""

    @DeleteMapping("/{id}")
      // @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Clients> optionalClients = service.delete(id);
        if (optionalClients.isPresent()) {
            return ResponseEntity.ok(optionalClients.orElseThrow());

        }
        return ResponseEntity.notFound().build();
    }


}
