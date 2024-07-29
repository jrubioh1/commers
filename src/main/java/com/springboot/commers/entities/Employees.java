package com.springboot.commers.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
public class Employees extends User{

    


    
    public Employees() {
    }

    public Employees(String name, String email, String password, List<Rol> roles) {
        super(name, email, password, roles);
    }

}
