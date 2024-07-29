package com.springboot.commers.entities;

import java.util.List;



import jakarta.persistence.Entity;

import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity(name = "users")
@Table(name = "employee")
@PrimaryKeyJoinColumn(referencedColumnName = "user")
public class Employee extends User {
 

    public Employee() {
    }

    public Employee(String name, String email, String password, List<Rol> roles) {
        super(name, email, password, roles);
    }
}

