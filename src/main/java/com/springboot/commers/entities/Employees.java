package com.springboot.commers.entities;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "employees")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
public class Employees extends User {

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "createBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> productsCreated;

    @OneToMany(mappedBy = "modifyBy", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> productsUpdated;

    public Employees() {
    }

    public Employees(String name, String email, String password, List<Rol> roles) {
        super(name, email, password, roles);

    }

    public Employees(Long id) {
        super(id);
    }

    @Override
    public int hashCode() {
       return super.hashCode();
    }


    @Override
    public boolean equals(Object obj) {
   
       return  super.equals(obj);
    }
    


}
