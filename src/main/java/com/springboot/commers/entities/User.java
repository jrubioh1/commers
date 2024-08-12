package com.springboot.commers.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.springboot.commers.validations.ExistsByUsername;
import com.springboot.commers.validations.OnCreate;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;
    @NotBlank
    @Email
    @ExistsByUsername(groups = {OnCreate.class})
    private String email;

    @NotBlank(groups = {OnCreate.class})
    private String password;

    @Column(name = "serial_user")
    private String serialUser;

    @ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "rol_id"))
    @JsonIgnoreProperties({ "users", "hibernateLazyInitializer", "handler" })
    private List<Rol> roles = new ArrayList<>();

    public User() {
    }

    public User(String name, String email, String password, List<Rol> roles) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public User(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", roles=" + roles
                + "]";
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
        User other = (User) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

}