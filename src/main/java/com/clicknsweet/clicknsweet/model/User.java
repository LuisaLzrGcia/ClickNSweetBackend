package com.clicknsweet.clicknsweet.model;


import jakarta.persistence.*;
//import com.clicknsweet.clicknsweet.model.Order;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name ="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    @Column(name = "first_name", length = 60, nullable = false, unique = true)
    private String firstName;

    @Column(name = "last_name", length = 60, nullable = false, unique = true)
    private String lastName;

    @Column(name = "user_name", length = 60, nullable = false, unique = true)
    private String userName;

    @Column(name = "email", length = 60, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 60, nullable = false)
    private String password;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updateAt")
    private LocalDateTime update_at;

    @Column(name = "phone", length = 20)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @OneToMany(mappedBy = "user", cascade =  CascadeType.ALL)
    private List<Address> adres;

   // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    //private List<Order> orders;

   // @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    //private Cart cart;

    public User() {
    }

    public User(Long id, String first_name, String last_name, String user_name, String email, String password, LocalDateTime last_login, LocalDateTime created_at, LocalDateTime update_at, String phone, Role role) {
        this.id = id;
        this.firstName = first_name;
        this.lastName = last_name;
        this.userName = user_name;
        this.email = email;
        this.password = password;
        this.lastLogin = last_login;
        this.createdAt = created_at;
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

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }

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
        return lastLogin;
    }

    public void setLast_login(LocalDateTime last_login) {
        this.lastLogin = last_login;
    }

    public LocalDateTime getCreated_at() {
        return createdAt;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.createdAt = created_at;
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
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", roleId=" + (role != null ? role.getId() : null) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(userName, user.userName) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(lastLogin, user.lastLogin) && Objects.equals(createdAt, user.createdAt) && Objects.equals(update_at, user.update_at) && Objects.equals(phone, user.phone) && Objects.equals(role, user.role);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, userName, email, password, lastLogin, createdAt, update_at, phone, role);
    }
}

