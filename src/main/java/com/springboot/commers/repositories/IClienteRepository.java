package com.springboot.commers.repositories;


import org.springframework.data.repository.CrudRepository;


import com.springboot.commers.entities.Client;


public interface IClienteRepository  extends CrudRepository<Client, Long>{



}
