package com.springboot.commers.repositories;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;


import com.springboot.commers.entities.Rol;



public interface IRolRepository extends CrudRepository<Rol, Long> {

    Optional<Rol>  findByName(String name);


}
