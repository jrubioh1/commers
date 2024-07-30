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


    
    public Employees() {
    }

    
    public Employees(String name, String email, String password, List<Rol> roles, List<Invoice> invoices) {
        super(name, email, password, roles);
        this.invoices = invoices;
    }

    


    public Employees(Long id) {
        super(id);
    }


    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    
}
