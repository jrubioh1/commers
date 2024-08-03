package com.springboot.commers.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.commers.entities.Clients;
import com.springboot.commers.entities.Rol;
import com.springboot.commers.repositories.IClienteRepository;
import com.springboot.commers.repositories.IRolRepository;


@Service
public class ClientServiceImpl implements IClientService{

    @Autowired
    private IClienteRepository repository;

    @Autowired
    private IRolRepository repositoryRol;


    @Override
    @Transactional(readOnly = true)
    public List<Clients> findAll() {
       return  (List<Clients>) repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Clients> findById(Long id) {

        return repository.findById(id);
      
    }

    @Override
    @Transactional()
    public Clients save(Clients client) {
        return repository.save(client);
    }

    @Override
    @Transactional()
    public Optional<Clients> update(Long id, Clients client) {
        Optional<Clients> optionalClient= repository.findById(id);
        if(optionalClient.isPresent()){
            Clients clientDb=optionalClient.orElseThrow();
            clientDb.setName(client.getName()); 
            clientDb.setEmail(client.getEmail());
            clientDb.setPassword(client.getPassword());
            return Optional.of(repository.save(clientDb));

        }

        return optionalClient;
    }

    @Override
    @Transactional()
    public Optional<Clients> updateRoles(Long id, List<Rol> roles) {
        
        Optional<Clients> optionalClient = repository.findById(id);
        if (optionalClient.isPresent()) {
            Clients clientDb = optionalClient.get();
    
            // Crear una lista de roles existentes
            List<Rol> rolesDb = new ArrayList<>();
            
            // Iterar sobre los roles solicitados y buscar cada uno en la base de datos
            for (Rol rol : roles) {
                // Buscar el rol por su nombre
                Optional<Rol> existingRol = repositoryRol.findByName(rol.getName());
                if (existingRol.isPresent()) {
                    // Añadir el rol existente a la lista
                    rolesDb.add(existingRol.get());
                } else {
                    // Si no se encuentra el rol, podrías optar por lanzar una excepción
                    throw new IllegalArgumentException("Rol no encontrado: " + rol.getName());
                }
            }
    
            // Asignar los roles existentes al cliente
            clientDb.setRoles(rolesDb);
    
            // Guardar y devolver el cliente actualizado
            return Optional.of(repository.save(clientDb));
        }
    
        // Retornar vacío si el cliente no fue encontrado
        return optionalClient;
    }

    @Override
    @Transactional()
    public Optional<Clients> delete(Long id) {
        Optional<Clients> optionalClient= repository.findById(id);
        optionalClient.ifPresent(((clientDb)->{
             repository.delete(clientDb);

        }));
        return optionalClient;
        
    }

}
