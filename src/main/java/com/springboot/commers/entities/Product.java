package com.springboot.commers.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.springboot.commers.validations.IsExistsDb;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;
    private Integer stock;
    
    @IsExistsDb
    private String serial;

   
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "create_by_id")
    @JsonIgnoreProperties({"productsCreated", "productsUpdated", "roles", "invoices", "password","hibernateLazyInitializer", "handler"})
    private Employees createBy;

   
    private LocalDateTime createAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "update_by_id")
    @JsonIgnoreProperties({"productsCreated", "productsUpdated", "roles", "invoices", "password","hibernateLazyInitializer", "handler"})
    private Employees modifyBy;

    private LocalDateTime modifyAt;

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    @JsonIgnore
    @NotNull
    private List<LineInvoice> linesInvoice=new ArrayList<>();;

    public Product() {
    }

    public Product(String name, Double price, Integer stock, String serial) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.serial=serial;
    }

    public Product(Long id) {
        this.id = id;
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
        Product other = (Product) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Product [id=" + id + ", name=" + name + ", price=" + price + ", stock=" + stock + ", serial=" + serial
                + ", createBy=" + createBy + ", createAt=" + createAt + ", modifyBy=" + modifyBy + ", modifyAt="
                + modifyAt + ", linesInvoice=" + linesInvoice + "]";
    }

    
}
