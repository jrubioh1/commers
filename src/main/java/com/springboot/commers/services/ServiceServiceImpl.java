package com.springboot.commers.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.springboot.commers.entities.Employee;
import com.springboot.commers.entities.Service;
import com.springboot.commers.repositories.IServiceRepository;

public class ServiceServiceImpl implements IServiceService{

    private IServiceRepository repository;

    @Override
    public List<Service> findAll() {
         return (List<Service>) repository.findAll();
    }

    @Override
    public Optional<Service> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Service save(Service service, Employee employee) {

        service.setCreateBy(employee);
        service.setCreateAt(LocalDateTime.now());
       return repository.save(service);
    }

    @Override
    public Optional<Service> update(Long id, Service service, Employee employee) {
        Optional<Service> optionalService= repository.findById(id);

        if(optionalService.isPresent()){
            Service serviceDb= optionalService.orElseThrow();
            serviceDb.setName(service.getName());
            serviceDb.setPrice(service.getPrice());
            serviceDb.setDescription(service.getDescription());
            serviceDb.setModifyBy(employee);
            serviceDb.setModifyAt(LocalDateTime.now());

            return Optional.of( repository.save(serviceDb));

        }

        return optionalService;
    }

    @Override
    public Optional<Service> delete(Long id) {
       Optional<Service> optionalService= repository.findById(id);

       optionalService.ifPresent((serviceDb)->{
        repository.delete(serviceDb);
       });

       return optionalService; 
    }

}
