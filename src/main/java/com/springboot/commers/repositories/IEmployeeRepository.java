package com.springboot.commers.repositories;
import org.springframework.data.repository.CrudRepository;


import com.springboot.commers.entities.Employee;

public interface IEmployeeRepository extends CrudRepository<Employee, Long> {

}
