package com.springboot.commers.services;

import java.util.List;
import java.util.Optional;

import com.springboot.commers.entities.Employee;
import com.springboot.commers.entities.Service;

public interface IServiceService {

    List<Service> findAll();
    Optional<Service> findById(Long id);
    Service save(Service service, Employee employee);
    Optional<Service>  update(Long id, Service service, Employee employee);
    Optional<Service> delete(Long id);


}
