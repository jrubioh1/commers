package com.springboot.commers.helpers;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.commers.entities.Rol;
import com.springboot.commers.entities.User;
import com.springboot.commers.repositories.IUsersRepository;
import com.springboot.commers.services.IRolService;

@Component
public class UserHelpers {

    // Se inicializan a traves de contructor porque al ser final no se puede
    // inicializar directamente con @Autowired
    // Al ser una clase que se va a usar dentro de otras clases que poseen
    // inyecciones, es mejor usar campos finales para
    // evitar posibles problemas de sobreinyecciones al pasarse a la otra clase
    // donde sera usada.

    private final IUsersRepository repository;

    private final IRolService serviceRol;

    // @Autowired //Se puede borrar la anotacion pero se deja para que sea mas
    // explicito que lo parametos viene de inyecciones
    public UserHelpers(IUsersRepository userRepository, IRolService rolService) {
        this.repository = userRepository;
        this.serviceRol = rolService;
    }

    @Transactional()
    public Optional<User> updateUser(Long id, User user) {
        Optional<User> optionalUser = repository.findById(id);
        if (optionalUser.isPresent()) {
            User userDB = optionalUser.orElseThrow();
            userDB.setName(user.getName());
            userDB.setEmail(user.getEmail());
            userDB.setPassword(user.getPassword());
            userDB.setRoles(listOfRolesDb(user.getRoles()));
            return Optional.of(repository.save(userDB));

        }

        return optionalUser;
    }

    @Transactional()
    public Optional<User> deleteUser(Long id) {
        Optional<User> optionalUser = repository.findById(id);
        optionalUser.ifPresent(((userDb) -> {
            repository.delete(userDb);

        }));
        return optionalUser;

    }

    @Transactional(readOnly = true)
    public List<Rol> listOfRolesDb(List<Rol> roles) {
        return roles.stream()
                .map(rol -> serviceRol.findByName(rol.getName())
                        .orElseThrow(() -> new RuntimeException("Role not found: " + rol.getName())))
                .collect(Collectors.toList());
    }

}
