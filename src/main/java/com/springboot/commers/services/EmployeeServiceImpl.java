package com.springboot.commers.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.commers.entities.Employees;
import com.springboot.commers.repositories.IEmployeeRepository;

@Service
public class EmployeeServiceImpl  implements IEmployeeService{

    
    private final  IEmployeeRepository repository; 




    //@Autowired
    public EmployeeServiceImpl(IEmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employees> findAll() {
        
        return (List<Employees>)repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)

    public Optional<Employees> findById(Long id) {

        return repository.findById(id);
        
    }

    @Override
    @Transactional()
    public Employees save(Employees employee) {
       return repository.save(employee);

    }

    @Override
    @Transactional()
    public Optional<Employees> update(Long id, Employees employee) {
        Optional<Employees> optionalEmployee= repository.findById(id);
        if(optionalEmployee.isPresent()){
            Employees employeeDb= optionalEmployee.orElseThrow();
            employeeDb.setEmail(employee.getEmail());
            employeeDb.setName(employee.getName());
            employeeDb.setPassword(employee.getPassword());
            employeeDb.setRoles(employee.getRoles());
            return Optional.of(repository.save(employeeDb));
        } 

        return optionalEmployee; 

    }

    @Override
    @Transactional()
    public Optional<Employees> delete(Long id) {
        Optional<Employees> optonalEmployee= repository.findById(id);
        optonalEmployee.ifPresent(employeeDb->{
             repository.delete(employeeDb);
        });

        return optonalEmployee;

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Employees> findByName(String name) {
        return repository.findByName(name);
    }

}
