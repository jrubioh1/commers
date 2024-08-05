package com.springboot.commers.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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

    
    // el remove se hace  manual para pdoer gestionar el stock de los productos
    @OneToMany(mappedBy = "invoice", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JsonIgnoreProperties({ "invoice", "hibernateLazyInitializer", "handler" })
    @NotEmpty
    private List<LineInvoice> linesInvoice = new ArrayList<>();;

    
    private Double whole=0.0;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "employee_id")
    @JsonIgnoreProperties({"productsCreated", "productsUpdated", "roles", "invoices", "password","hibernateLazyInitializer", "handler"})
    @NotNull
    private Employees employee;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH})
    @JoinColumn(name = "client_id")
    @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" , "invoices"})
    @NotNull
    private Clients client;

    private LocalDateTime dateTime= LocalDateTime.now();

    public Invoice() {
    }

    public Invoice(List<LineInvoice> linesInvoice, Double whole, Employees employee, Clients client) {
        this.linesInvoice = linesInvoice;
        this.whole = whole;
        this.employee = employee;
        this.client = client;
    }

    public Invoice(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Invoice [id=" + id + ", linesInvoice=" + linesInvoice + ", whole=" + whole + ", employee=" + employee
                + ", client=" + client + ", dateTime=" + dateTime + "]";
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
