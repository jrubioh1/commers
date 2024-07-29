package com.springboot.commers.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.commers.entities.Employee;
import com.springboot.commers.repositories.IEmployeeRepository;

@Service
public class EmployeeServiceImpl  implements IEmployeeService{

    @Autowired
    private IEmployeeRepository repository; 

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findAll() {
        
        return (List<Employee>)repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)

    public Optional<Employee> findById(Long id) {

        return repository.findById(id);
        
    }

    @Override
    @Transactional()
    public Employee save(Employee employee) {
       return repository.save(employee);

    }

    @Override
    @Transactional()
    public Optional<Employee> update(Long id, Employee employee) {
        Optional<Employee> optionalEmployee= repository.findById(id);
        if(optionalEmployee.isPresent()){
            Employee employeeDb= optionalEmployee.orElseThrow();
            employeeDb.setEmail(employee.getEmail());
            employeeDb.setName(employee.getName());
            employeeDb.setPassword(employee.getPassword());
            employeeDb.setRoles(employee.getRoles());
            return Optional.of(repository.save(employeeDb));
        } 

        return optionalEmployee; 

    }

    @Override
    public Optional<Employee> delete(Long id) {
        Optional<Employee> optonalEmployee= repository.findById(id);
        optonalEmployee.ifPresent(employeeDb->{
             repository.delete(employeeDb);
        });

        return optonalEmployee;

    }

}
