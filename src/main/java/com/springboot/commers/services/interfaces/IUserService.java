package com.springboot.commers.services.interfaces;

import com.springboot.commers.entities.Clients;
import com.springboot.commers.entities.Employees;
import com.springboot.commers.entities.User;
import java.util.List;
import java.util.Optional;

public interface IUserService {

    /**
     * Guarda un nuevo usuario en la base de datos.
     *
     * @param user el usuario a guardar.
     * @return el usuario guardado.
     */
    User save(User user);

    /**
     * Actualiza un usuario existente en la base de datos.
     *
     * @param user el usuario a actualizar.
     * @return el usuario actualizado.
     */
    Optional<User> update(User user);

    /**
     * Encuentra un usuario por su ID.
     *
     * @param id el ID del usuario a encontrar.
     * @return un Optional con el usuario si se encuentra, o vacío si no.
     */
    Optional<User> findById(Long id);

    /**
     * Elimina un usuario por su ID.
     *
     * @param id el ID del usuario a eliminar.
     */
    void deleteById(Long id);

    /**
     * Encuentra todos los usuarios.
     *
     * @return una lista de todos los usuarios.
     */
    List<User> findAll();

    /**
     * Verifica si existe un usuario con el nombre de usuario dado.
     *
     * @param username el nombre de usuario a verificar.
     * @return true si el usuario existe, false si no.
     */
    boolean existsByEmail(String username);


    Optional<User> findByEmail(String email);
    Optional<Clients> findClientByEmail(String email); 
    Optional<Employees> findEmployeeByEmail(String email);
}
