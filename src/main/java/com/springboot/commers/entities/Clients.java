package com.springboot.commers.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "clients")
@Getter
@Setter
public class Clients extends User {

    
    @OneToMany(mappedBy = "client")  
    private List<Invoice> invoices; 

       public Clients() {
    }


    public Clients(String name, String email, String password, List<Rol> roles, List<Invoice> invoices) {
        super(name, email, password, roles);
        this.invoices = invoices;
    }


    public Clients(Long id) {
        super(id);
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    
}
