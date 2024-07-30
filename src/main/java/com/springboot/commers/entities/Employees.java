package com.springboot.commers.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
public class Employees extends User{

    @OneToMany(mappedBy = "employee")  
    private List<Invoice> invoices; 

    @OneToMany(mappedBy = "createBy")
    private List<Product> productsCreated;

    @OneToMany(mappedBy = "modifyBy")
    private List<Product> producsUpdates;



    
    public Employees() {
    }

    
    public Employees(String name, String email, String password, List<Rol> roles) {
        super(name, email, password, roles);

    }

    


    public Employees(Long id) {
        super(id);
    }


    public List<Invoice> getInvoices() {
        return invoices;
    }

  

    
}
