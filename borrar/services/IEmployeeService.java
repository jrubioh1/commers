package com.springboot.commers.services;

import java.util.List;
import java.util.Optional;

import com.springboot.commers.entities.Employee;

public interface IEmployeeService {

    List<Employee> findAll();
    Optional<Employee> findById(Long id);
    Employee save(Employee employee);
    Optional<Employee> update(Long id, Employee employee);
    Optional<Employee> delete(Long id); 
    Optional<Employee> findByName(String name);


}
