package com.springboot.commers.entities;

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
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "address")
@Getter
@Setter
public class Address {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @OneToMany(mappedBy = "shippingAddress", cascade = { CascadeType.PERSIST, CascadeType.MERGE,
                        CascadeType.REFRESH,
                        CascadeType.DETACH })
        @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
        private List<Orders> shippingOrders;

        @OneToMany(mappedBy = "billingAddress", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
                        CascadeType.DETACH })
        @JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
        private List<Orders> billingOrders;

        @ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
                        CascadeType.DETACH })
        @JoinColumn(name = "client_id")
        private Clients client;

        @NotBlank
        private String street;
        @NotBlank
        private String city;
        @NotBlank
        private String state;
        @NotBlank
        private String postalCode;
        @NotBlank
        private String country;

        

        @Override
        public String toString() {
                return "Address [id=" + id + ", shippingOrders=" + shippingOrders + ", billingOrders=" + billingOrders
                                + ", client=" + client + ", street=" + street + ", city=" + city + ", state=" + state
                                + ", postalCode=" + postalCode + ", country=" + country + "]";
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
                Address other = (Address) obj;
                if (id == null) {
                        if (other.id != null)
                                return false;
                } else if (!id.equals(other.id))
                        return false;
                return true;
        }

}
