package com.springboot.commers.entities;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "invoice")
public class Invoice {

    private Long id;

    @OneToMany
    private List<Product> products;

    private Double whole;
    private Employee employee;


    private Client client;


    @Temporal(TemporalType.TIMESTAMP)
    private Date date;


    

    public Invoice() {
    }

    

    public Invoice(List<Product> products, Double whole, Employee employee, Client client, Date date) {
        this.products = products;
        this.whole = whole;
        this.employee = employee;
        this.client = client;
        this.date = date;
    }

    



    public Invoice(Long id) {
        this.id = id;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Double getWhole() {
        return whole;
    }

    public void setWhole(Double whole) {
        this.whole = whole;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    



    @Override
    public String toString() {
        return "Invoice [id=" + id + ", products=" + products + ", whole=" + whole + ", employee=" + employee
                + ", client=" + client + ", date=" + date + "]";
    }



    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }



    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Invoice other = (Invoice) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    


    



    

}
