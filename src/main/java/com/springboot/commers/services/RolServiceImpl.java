package com.springboot.commers.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.commers.entities.Rol;
import com.springboot.commers.repositories.IRolRepository;
import com.springboot.commers.services.interfaces.IRolService;

@Service
public class RolServiceImpl implements IRolService {

    private final IRolRepository repository;

    

    //@Autowired
    public RolServiceImpl(IRolRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Rol> findAll() {
       return (List<Rol>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Rol> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional()
    public Rol save(Rol rol) {
        return repository.save(rol);
    }

    @Override
    @Transactional()
    public Optional<Rol> update(Long id, Rol rol) {
        
        Optional<Rol> optionalRol= repository.findById(id);

        if(optionalRol.isPresent()){
            Rol rolDb= optionalRol.orElseThrow();
            rolDb.setName(rol.getName());
            rolDb.setUsers(rol.getUsers());
             return Optional.of(repository.save(rolDb));
        }
        return optionalRol;
    }

    @Override
    @Transactional()
    public Optional<Rol> delete(Long id) {
       Optional<Rol> optionalRol= repository.findById(id);
       optionalRol.ifPresent((rolDb)->{
            repository.delete(rolDb);

       });

       return optionalRol; 
    }

  
    @Override
    @Transactional(readOnly = true)
    public Optional<Rol> findByName(String  name) {
       return repository.findByName(name);
    }

}
