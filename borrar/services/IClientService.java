package com.springboot.commers.services;

import java.util.List;
import java.util.Optional;

import com.springboot.commers.entities.Client;

public interface IClientService {

    List<Client> findAll();
    Optional<Client> findById(Long id);
    Client save(Client client); 
    Optional<Client> update(Long id, Client client);
    Optional<Client> delete(Long id);

}
