package com.springboot.commers.services;

import java.util.List;
import java.util.Optional;

import com.springboot.commers.entities.Employees;

public interface IEmployeeService {

    List<Employees> findAll();
    Optional<Employees> findById(Long id);
    Employees save(Employees employee);
    Optional<Employees> update(Long id, Employees employee);
    Optional<Employees> delete(Long id); 
    Optional<Employees> findByName(String name);
    Employees getEmployeeDb(Employees employee);
    boolean existsByEmail(String username);


}
