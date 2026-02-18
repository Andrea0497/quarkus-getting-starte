package org.acme.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role extends ModelBase {
    @Column(name = "description")
    public String description;

    @ManyToMany(mappedBy = "roles")
    public Set<User> users = new HashSet<>();
}