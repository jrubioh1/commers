package com.springboot.commers.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.springboot.commers.entities.Rol;
import com.springboot.commers.repositories.IRolRepository;

public class RolServiceImpl implements IRolService {

    @Autowired
    private IRolRepository repository;

    @Override
    public List<Rol> findAll() {
       return (List<Rol>) repository.findAll();
    }

    @Override
    public Optional<Rol> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Rol save(Rol rol) {
        return repository.save(rol);
    }

    @Override
    public Optional<Rol> update(Long id, Rol rol) {
        
        Optional<Rol> optionalRol= repository.findById(id);

        if(optionalRol.isPresent()){
            Rol rolDb= optionalRol.orElseThrow();
            rolDb.setName(rol.getName());
            rolDb.setEmployees(rol.getEmployees());
            rolDb.setClients(rol.getClients());
             return Optional.of(repository.save(rolDb));
        }
        return optionalRol;
    }

    @Override
    public Optional<Rol> delete(Long id) {
       Optional<Rol> optionalRol= repository.findById(id);
       optionalRol.ifPresent((rolDb)->{
            repository.delete(rolDb);

       });

       return optionalRol; 
    }

}
