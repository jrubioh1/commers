package com.springboot.commers.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.springboot.commers.entities.Clients;
import com.springboot.commers.entities.Employees;
import com.springboot.commers.entities.User;
import java.util.Optional;

public interface IUsersRepository extends CrudRepository<User, Long> {

  /**
   * Verifica si existe un usuario con el nombre de usuario dado.
   *
   * @param username el nombre de usuario a verificar.
   * @return true si el usuario existe, false si no.
   */
  boolean existsByEmail(String username);

  Optional<User> findByEmail(String email);

  @Query("SELECT c FROM Clients c JOIN User u ON u.id=c.id WHERE u.email= :email")
  Optional<Clients> findClientByEmail(@Param("email") String email);

  @Query("SELECT e FROM Employees e JOIN User u ON u.id=e.id WHERE u.email= :email")
  Optional<Employees> findEmployeeByEmail(@Param("email") String email);

}
