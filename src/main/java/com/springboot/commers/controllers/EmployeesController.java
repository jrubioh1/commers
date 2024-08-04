package com.springboot.commers.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.springboot.commers.services.IEmployeeService;


@CrossOrigin(origins = "http://localhost:4200", originPatterns = "*")
@RestController
@RequestMapping("/api/employees")
public class EmployeesController {

    private final IEmployeeService service;
    


    //@Autowired
   

    @GetMapping
    public List<Employees> list(){
        return service.findAll();
    }

    public EmployeesController(IEmployeeService service) {
        this.service = service;
    
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> view(@PathVariable Long id) {

        Optional<Employees> optionalEmployees= service.findById(id);
        if(optionalEmployees.isPresent()){
            return ResponseEntity.ok(optionalEmployees.orElseThrow());
        }

        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> create (@RequestBody Employees employee){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(employee));
    }

    @PutMapping("/{id}")
     // @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@RequestBody Employees employee, @PathVariable Long id) {


        Optional<Employees> optionalEmployee = service.update(id,employee);
        if (optionalEmployee.isPresent()) {
            return ResponseEntity.ok(optionalEmployee.orElseThrow());

        }
        return ResponseEntity.notFound().build();
    }


    @DeleteMapping("/{id}")
      // @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<Employees> optionalEmployee = service.delete(id);
        if (optionalEmployee.isPresent()) {
            return ResponseEntity.ok(optionalEmployee.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }



    
    


}
