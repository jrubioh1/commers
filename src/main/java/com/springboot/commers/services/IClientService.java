package com.springboot.commers.services;

import java.util.List;
import java.util.Optional;

import com.springboot.commers.entities.Clients;
import com.springboot.commers.entities.Rol;

public interface IClientService {

    List<Clients> findAll();
    Optional<Clients> findById(Long id);
    Clients save(Clients client); 
    Optional<Clients> update(Long id, Clients client);
    Optional<Clients> delete(Long id);
    Optional<Clients> updateRoles(Long id, List<Rol> roles);

}
