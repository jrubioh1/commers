package com.springboot.commers.services;

import com.springboot.commers.entities.Clients;
import com.springboot.commers.entities.Employees;
import com.springboot.commers.entities.User;
import com.springboot.commers.repositories.IUsersRepository;
import com.springboot.commers.services.interfaces.IUserService;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class UserServiceImpl implements IUserService {

    private final IUsersRepository userRepository;

    


    //@Autowired
    public UserServiceImpl(IUsersRepository userRepository) {
        this.userRepository = userRepository;
    }

    

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> update(User user) {
        // Verificamos si el usuario existe antes de actualizar
        if (userRepository.existsById(user.getId())) {
            User updatedUser = userRepository.save(user);
            return Optional.of(updatedUser);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return (List<User>)  userRepository.findAll();
    }

    @Override
    public boolean existsByEmail(String username) {
        return userRepository.existsByEmail(username);
    }



    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }



    @Override
    public Optional<Clients> findClientByEmail(String email) {

        return userRepository.findClientByEmail(email);
    
    }



    @Override
    public Optional<Employees> findEmployeeByEmail(String email) {

        return userRepository.findEmployeeByEmail(email);

    }
}
