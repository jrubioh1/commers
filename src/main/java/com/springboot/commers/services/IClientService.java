package com.springboot.commers.services;

import java.util.List;
import java.util.Optional;

import com.springboot.commers.entities.Clients;


public interface IClientService {

    List<Clients> findAll();
    Optional<Clients> findById(Long id);
    Clients save(Clients client); 
    Optional<Clients> update(Long id, Clients client);
    Optional<Clients> delete(Long id);
    Clients getClientDb(Clients client);
    boolean existsByEmail (String username);



}
