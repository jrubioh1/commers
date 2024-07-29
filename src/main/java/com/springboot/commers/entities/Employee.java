package com.springboot.commers.entities;

import java.util.List;



import jakarta.persistence.Entity;

import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee extends User {

    public Employee() {
    }

    public Employee(String name, String email, String password, List<Rol> roles) {
        super(name, email, password, roles);
    }
}

