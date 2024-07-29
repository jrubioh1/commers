package com.springboot.commers.entities;

import java.time.LocalDateTime;

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
@Table(name = "service")
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Double price;
   
   
   
    private Employee createBy;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createAt;


    private Employee modifyBy;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime modifyAt;

    public Service() {
    }

    public Service(String name, String description, Double price) {
        this.name = name;
        this.description = description;
        this.price = price;
      
    }

    public Long getId() {
        return id;
    }

    public Service(Long id) {
        this.id = id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


    public Employee getCreateBy() {
        return createBy;
    }

    public void setCreateBy(Employee createBy) {
        this.createBy = createBy;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public Employee getModifyBy() {
        return modifyBy;
    }

    public void setModifyBy(Employee modifyBy) {
        this.modifyBy = modifyBy;
    }

    public LocalDateTime getModifyAt() {
        return modifyAt;
    }

    public void setModifyAt(LocalDateTime modifyAt) {
        this.modifyAt = modifyAt;
    }

    @Override
    public String toString() {
        return "Service [id=" + id + ", name=" + name + ", description=" + description + ", price=" + price  + ", createBy=" + createBy + ", createAt=" + createAt + ", modifyBy=" + modifyBy + ", modifyAt="
                + modifyAt + "]";
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
        Service other = (Service) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }


    

}
