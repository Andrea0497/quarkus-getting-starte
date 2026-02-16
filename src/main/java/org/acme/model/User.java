package org.acme.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User extends ModelBase {
    public String firstName;
    public String lastName;
    public String email;   
}