package com.springboot.commers.services;

import java.util.List;
import java.util.Optional;

import com.springboot.commers.entities.Rol;

public interface IRolService {
    List<Rol> findAll();
    Optional<Rol> findById(Long id);
    Rol save(Rol rol);
    Optional<Rol>  update(Long id, Rol rol);
    Optional<Rol> delete(Long id);


}
