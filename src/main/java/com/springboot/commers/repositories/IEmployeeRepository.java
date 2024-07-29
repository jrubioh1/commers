package com.springboot.commers.repositories;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;


import com.springboot.commers.entities.Employee;

public interface IEmployeeRepository extends CrudRepository<Employee, Long> {

    Optional<Employee> findByName(String name);

}
