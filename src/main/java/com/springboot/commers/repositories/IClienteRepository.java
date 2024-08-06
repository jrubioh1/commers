package com.springboot.commers.repositories;


import org.springframework.data.repository.CrudRepository;


import com.springboot.commers.entities.*;


public interface IClienteRepository  extends CrudRepository<Clients, Long>{
    boolean existsByUsername(String username);


}
