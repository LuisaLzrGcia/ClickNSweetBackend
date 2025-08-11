package com.clicknsweet.clicknsweet.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @Column (name = "role_id")
    private Integer id;

    @Column( name = "role_type")
    private String type;

    @OneToMany(mappedBy = "role")
    private List<User> users;

    public Role(Integer id, String type, List<User> users) {
        this.id = id;
        this.type = type;
        this.users = users;
    }

    public Role() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", users=" + users +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role role)) return false;
        return Objects.equals(id, role.id) && Objects.equals(type, role.type) && Objects.equals(users, role.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, users);
    }
}
