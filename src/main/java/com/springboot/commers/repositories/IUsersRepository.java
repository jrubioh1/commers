package com.springboot.commers.repositories;

import org.springframework.data.repository.CrudRepository;

import com.springboot.commers.entities.User;

public interface IUsersRepository  extends CrudRepository<User, Long>{



   /**
     * Verifica si existe un usuario con el nombre de usuario dado.
     *
     * @param username el nombre de usuario a verificar.
     * @return true si el usuario existe, false si no.
     */
    boolean existsByUsername(String username);

    

}
