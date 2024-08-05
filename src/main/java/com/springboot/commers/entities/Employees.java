package com.springboot.commers.entities;

import java.util.ArrayList;
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

    @OneToMany(mappedBy = "employee", cascade =   {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
    private List<Invoice> invoices=new ArrayList<>();

    @OneToMany(mappedBy = "createBy",cascade =   {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
    private List<Product> productsCreated= new ArrayList<>();

    @OneToMany(mappedBy = "modifyBy", cascade =   {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}, fetch = FetchType.LAZY)
    private List<Product> productsUpdated=new ArrayList<>();

 
    private Boolean active=true;

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
