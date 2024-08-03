package com.springboot.commers.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "clients")
@PrimaryKeyJoinColumn(name = "id")
@Getter
@Setter
public class Clients extends User {

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
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


    @Override
    public int hashCode() {
       return super.hashCode();
    }


    @Override
    public boolean equals(Object obj) {
   
       return  super.equals(obj);
    }
}
