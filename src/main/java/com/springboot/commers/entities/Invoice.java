package com.springboot.commers.entities;

import java.util.Date;
import java.util.List;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "invoice")
@Getter
@Setter
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "invoice")
    private List<LineInvoice> linesInvoice;

    private Double whole;

    @ManyToOne()
    @JoinColumn(name = "employee_id")
    private Employees employee;

    @ManyToOne()
    @JoinColumn(name = "client_id")
    private Clients client;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public Invoice() {
    }

    public Invoice(List<LineInvoice> linesInvoice, Double whole, Employees employee, Clients client, Date date) {
        this.linesInvoice = linesInvoice;
        this.whole = whole;
        this.employee = employee;
        this.client = client;
        this.date = date;
    }

    public Invoice(Long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Invoice [id=" + id + ", linesInvoice=" + linesInvoice + ", whole=" + whole + ", employee=" + employee
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
