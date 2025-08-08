package com.clicknsweet.clicknsweet.model;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;


@Entity
@Table(name ="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser")
    private Long id;

    @Column(name = "firstName", length = 60, nullable = false, unique = true)
    private String first_name;

    @Column(name = "lasName", length = 60, nullable = false, unique = true)
    private String last_name;

    @Column(name = "userName", length = 60, nullable = false, unique = true)
    private String user_name;

    @Column(name = "email", length = 60, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 60, nullable = false)
    private String password;

    @Column(name = "lastLogin")
    private LocalDateTime last_login;

    @Column(name = "createdAt", updatable = false)
    private LocalDateTime created_at;

    @Column(name = " updateAt")
    private LocalDateTime update_at;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "roleId")
    private Role role;

    public User() {
    }

    public User(Long id, String first_name, String last_name, String user_name, String email, String password, LocalDateTime last_login, LocalDateTime created_at, LocalDateTime update_at, String phone, Role role) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.user_name = user_name;
        this.email = email;
        this.password = password;
        this.last_login = last_login;
        this.created_at = created_at;
        this.update_at = update_at;
        this.phone = phone;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getLast_login() {
        return last_login;
    }

    public void setLast_login(LocalDateTime last_login) {
        this.last_login = last_login;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(LocalDateTime update_at) {
        this.update_at = update_at;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", user_name='" + user_name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", last_login=" + last_login +
                ", created_at=" + created_at +
                ", update_at=" + update_at +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id) && Objects.equals(first_name, user.first_name) && Objects.equals(last_name, user.last_name) && Objects.equals(user_name, user.user_name) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(last_login, user.last_login) && Objects.equals(created_at, user.created_at) && Objects.equals(update_at, user.update_at) && Objects.equals(phone, user.phone) && Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, first_name, last_name, user_name, email, password, last_login, created_at, update_at, phone, role);
    }
}

