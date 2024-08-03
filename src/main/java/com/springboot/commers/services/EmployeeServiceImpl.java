package com.springboot.commers.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.commers.entities.Employees;
import com.springboot.commers.helpers.UserHelpers;
import com.springboot.commers.repositories.IEmployeeRepository;

@Service
public class EmployeeServiceImpl  implements IEmployeeService{

    
    private final  IEmployeeRepository repository; 



    private final UserHelpers userHelpers;



    //@Autowired
   
    @Override
    @Transactional(readOnly = true)
    public List<Employees> findAll() {
        
        return (List<Employees>)repository.findAll();
    }

    public EmployeeServiceImpl(IEmployeeRepository repository, UserHelpers userHelpers) {
        this.repository = repository;
        this.userHelpers = userHelpers;
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
        Employees employeeUpdated = (Employees) userHelpers.updateUser(id, employee).orElseThrow();
        return Optional.of(employeeUpdated);
    }

    @Override
    @Transactional()
    public Optional<Employees> delete(Long id) {
        Employees employeeDeleted = (Employees) userHelpers.deleteUser(id).orElseThrow();
        return Optional.of(employeeDeleted);

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Employees> findByName(String name) {
        return repository.findByName(name);
    }

}
