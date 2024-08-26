package com.springboot.commers.helpers;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.commers.entities.Employees;
import com.springboot.commers.entities.Rol;
import com.springboot.commers.entities.User;
import com.springboot.commers.repositories.IUsersRepository;
import com.springboot.commers.services.interfaces.IRolService;

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
            userDB.setLastname(user.getLastname());
            userDB.setTelephoneNumber(user.getTelephoneNumber());
            userDB.setEmail(user.getEmail());
            userDB.setPassword(user.getPassword());
            userDB.setRoles(listOfRolesDb(user.getRoles()));
            return Optional.of(userDB);

        }

        return optionalUser;
    }

    @Transactional()
    public Optional<User> deleteUser(Long id, Boolean isEmployee) {
        Optional<User> optionalUser = repository.findById(id);
        optionalUser.ifPresent(((userDb) -> {
            userDb.getRoles().clear();
            userDb.getInvoices().clear();

            if (isEmployee) {
                ((Employees) userDb).getProductsCreated().clear();
                ((Employees) userDb).getProductsUpdated().clear();

            }
            repository.delete(userDb);

        }));
        return optionalUser;

    }

    @Transactional(readOnly = true)
    public List<Rol> listOfRolesDb(List<Rol> roles) {
        return roles.stream()
                .map(rol -> serviceRol.findByName(rol.getName())
                        .orElseThrow(() -> new RuntimeException("Role (findByName) not found: " + rol.getName())))
                .collect(Collectors.toList());
    }

    public static String generateUserSerial(String name, String email, String hireDate) {
        try {

            String input = name + email + hireDate;

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(input.getBytes(StandardCharsets.UTF_8));

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al generar el hash: " + e.getMessage(), e);
        }
    }

    public boolean hasRole(Authentication authentication, String rol) {

        String roleName=String.format("ROLE_%s", rol);
        return authentication.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(roleName));
    }

}
