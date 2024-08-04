package com.springboot.commers.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.springboot.commers.entities.Clients;
import com.springboot.commers.entities.Rol;
import com.springboot.commers.repositories.IClienteRepository;

import com.springboot.commers.helpers.UserHelpers;

@Service
public class ClientServiceImpl implements IClientService {

    
    private final IClienteRepository repository;



    private final UserHelpers userHelpers;





    //@Autowired
    

    @Override
    @Transactional(readOnly = true)
    public List<Clients> findAll() {
        return (List<Clients>) repository.findAll();
    }

    public ClientServiceImpl(IClienteRepository repository, UserHelpers userHelpers) {
        this.repository = repository;
        this.userHelpers = userHelpers;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Clients> findById(Long id) {

        return repository.findById(id);

    }

    @Override
    @Transactional()
    // el rol de cliente se pondra siempre por defecto, pero en la vista no saldra disponible para ponerlo, evitando asi duplicidades, solo dejara poner o quitar roles adicionales para acumulacion de privilegios
    public Clients save(Clients client) {
        

        Rol clientRol= new Rol();
        clientRol.setName("ROLE_CLIENT");

        List<Rol> roles= new ArrayList<>();
        roles.add(clientRol);
        client.getRoles().forEach((rol)->{
            roles.add(rol);
        });

        List<Rol> rolesDb=userHelpers.listOfRolesDb(roles).stream().distinct().collect(Collectors.toList());
        client.setRoles( rolesDb);
        client.setSerialUser(UserHelpers.generateUserSerial(client.getName(), client.getEmail(), LocalDate.now().toString()));
        return repository.save(client);
    }

    @Override
    @Transactional()
    public Optional<Clients> update(Long id, Clients client) {
      Clients clientUpdated = (Clients) userHelpers.updateUser(id, client).orElseThrow();
      return Optional.of(clientUpdated);
    }

    @Override
    @Transactional()
    public Optional<Clients> delete(Long id) {
        Clients clientDeleted = (Clients) userHelpers.deleteUser(id).orElseThrow();
        return Optional.of(clientDeleted);

    }

    @Override
    @Transactional(readOnly = true)
    public Clients getClientDb(Clients client) {
        return repository.findById(client.getId()).orElseThrow();
    }


}
