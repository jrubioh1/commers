package com.springboot.commers.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "client")
public class Client extends User {



       public Client() {
    }

    public Client(String name, String email, String password, List<Rol> roles) {
        super(name, email, password, roles);
    }

}
