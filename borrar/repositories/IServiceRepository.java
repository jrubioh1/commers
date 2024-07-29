package com.springboot.commers.repositories;
import org.springframework.data.repository.CrudRepository;


import com.springboot.commers.entities.Service;


public interface IServiceRepository  extends CrudRepository<Service, Long>{

}
