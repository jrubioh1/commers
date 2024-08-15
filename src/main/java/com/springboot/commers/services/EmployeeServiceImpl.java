package com.springboot.commers.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.commers.entities.Employees;
import com.springboot.commers.entities.Rol;
import com.springboot.commers.helpers.UserHelpers;
import com.springboot.commers.repositories.IEmployeeRepository;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    private final IEmployeeRepository repository;

    private final UserHelpers userHelpers;


    @Autowired
    private PasswordEncoder passwordEncoder;
    // @Autowired

    @Override
    @Transactional(readOnly = true)
    public List<Employees> findAll() {

        return (List<Employees>) repository.findAll();
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
    // el rol de empleado se pondra siempre por defecto, pero en la vista no saldra
    // disponible para ponerlo, evitando asi duplicidades, solo dejara poner o
    // quitar roles adicionales para acumulacion de privilegios
    public Employees save(Employees employee) {

        Rol employeetRol = new Rol();
        employeetRol.setName("ROLE_EMPLOYEE");

        List<Rol> roles = new ArrayList<>();
        roles.add(employeetRol);
        employee.getRoles().forEach((rol) -> {

            roles.add(rol);
        });

        List<Rol> rolesDb = userHelpers.listOfRolesDb(roles).stream().distinct().collect(Collectors.toList());

        employee.setRoles(rolesDb);
        employee.setSerialUser(UserHelpers.generateUserSerial(employee.getName(), employee.getEmail(), LocalDate.now().toString()));
        employee.setPassword(passwordEncoder.encode(employee.getPassword()));
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
        
        Employees employeeDeleted = (Employees) userHelpers.deleteUser(id, true).orElseThrow();
        return Optional.of(employeeDeleted);

    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Employees> findByName(String name) {
        return repository.findByName(name);
    }

    @Transactional(readOnly = true)
    public Employees getEmployeeDb(Employees employee){ return repository.findById(employee.getId()).orElseThrow();}

    @Override
    public boolean existsByEmail(String username) {
        return repository.existsByEmail(username);
    }
    
   

}
