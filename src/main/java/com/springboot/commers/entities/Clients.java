package com.springboot.commers.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "clients")
public class Clients extends User {



       public Clients() {
    }

    public Clients(String name, String email, String password, List<Rol> roles) {
        super(name, email, password, roles);
    }

}
