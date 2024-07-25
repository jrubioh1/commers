package com.springboot.commers.entities;

import java.util.Date;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double price;
    private Integer stock;

    @ManyToMany
    @JoinColumn(name = "create_at")
    private Employee createBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;

    @ManyToMany
    @JoinColumn(name = "modify_by")
    private Employee modifyBy;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifyAt;



    
    public Product() {
    }

    

    public Product(String name, Double price, Integer stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    



    public Product(Long id) {
        this.id = id;
    }



    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
  
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }
    public Employee getCreateBy() {
        return createBy;
    }
    public void setCreateBy(Employee createBy) {
        this.createBy = createBy;
    }
    public Date getCreateAt() {
        return createAt;
    }
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
    public Employee getModifyBy() {
        return modifyBy;
    }
    public void setModifyBy(Employee modifyBy) {
        this.modifyBy = modifyBy;
    }
    public Date getModifyAt() {
        return modifyAt;
    }
    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
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
        return "Product [id=" + id + ", name=" + name + ", price=" + price + ", stock=" + stock + ", createBy="
                + createBy + ", createAt=" + createAt + ", modifyBy=" + modifyBy + ", modifyAt=" + modifyAt + "]";
    }

    

}
