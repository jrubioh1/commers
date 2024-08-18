package com.springboot.commers.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.springboot.commers.entities.Employees;


public interface IEmployeeRepository extends CrudRepository<Employees, Long> {

    Optional<Employees> findByName(String name);

    boolean existsByEmail(String username);

    Optional<Employees> findByEmail(String email);
}
