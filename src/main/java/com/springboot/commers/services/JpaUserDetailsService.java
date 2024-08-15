package com.springboot.commers.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.commers.entities.User;
import com.springboot.commers.repositories.IUsersRepository;

public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private IUsersRepository repository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = repository.findByEmail(username);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException(String.format("El usuario %s no existe en el sistema.", username));

        }

        User user = userOptional.orElseThrow();
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                user.getIsEnabled(), true, true, true, authorities);
    }

}
