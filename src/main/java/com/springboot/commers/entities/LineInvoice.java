package com.springboot.commers.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "line_invoice")
@Getter
@Setter
public class LineInvoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({ "invoice", "hibernateLazyInitializer", "handler" })

    @JoinColumn(name = "invoice_id")
    @NotNull
    private Invoice invoice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @NotNull
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Product product;

    @Min(1)
    private Integer quantity;
    
    @NotNull
    private Double amount;


    public LineInvoice() {
    }

    public LineInvoice(Invoice invoice, Product product, Double amount) {
        this.invoice = invoice;
        this.product = product;
        this.amount = amount;
    }

    public LineInvoice(Long id) {
        this.id = id;
    }

    

    @Override
    public String toString() {
        return "LineInvoice [id=" + id + ", invoice=" + invoice + ", product=" + product + ", quantity=" + quantity
                + ", amount=" + amount + "]";
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
        LineInvoice other = (LineInvoice) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    
}
