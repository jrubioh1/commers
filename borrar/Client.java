package com.springboot.commers.entities;

import java.util.List;

import jakarta.persistence.Entity;

import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "client")

public class Client {

   

    public Client() {
    }

    public Client(String name, String email, String password, List<Rol> roles) {
        super(name, email, password, roles);
    }
}