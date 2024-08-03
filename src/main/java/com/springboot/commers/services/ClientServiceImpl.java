package com.springboot.commers.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.commers.entities.Clients;

import com.springboot.commers.repositories.IClienteRepository;

import com.springboot.commers.helpers.UserHelpers;

@Service
public class ClientServiceImpl implements IClientService {

    
    private final IClienteRepository repository;


    private final UserHelpers userHelpers;





    //@Autowired
    public ClientServiceImpl(IClienteRepository repository, UserHelpers userHelpers) {
        this.repository = repository;
        this.userHelpers = userHelpers;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Clients> findAll() {
        return (List<Clients>) repository.findAll();
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
        Optional<Clients> optionalClient = repository.findById(id);
        if (optionalClient.isPresent()) {
            Clients clientDb = optionalClient.orElseThrow();
            clientDb.setName(client.getName());
            clientDb.setEmail(client.getEmail());
            clientDb.setPassword(client.getPassword());
            clientDb.setRoles( userHelpers.listOfRolesDb(client.getRoles()));
            return Optional.of(repository.save(clientDb));

        }

        return optionalClient;
    }

    @Override
    @Transactional()
    public Optional<Clients> delete(Long id) {
        Optional<Clients> optionalClient = repository.findById(id);
        optionalClient.ifPresent(((clientDb) -> {
            repository.delete(clientDb);

        }));
        return optionalClient;

    }

}
