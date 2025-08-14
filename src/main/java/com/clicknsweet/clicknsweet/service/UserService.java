package com.clicknsweet.clicknsweet.service;

import com.clicknsweet.clicknsweet.model.Role;
import com.clicknsweet.clicknsweet.model.User;
import com.clicknsweet.clicknsweet.repository.RoleRepository;
import com.clicknsweet.clicknsweet.repository.UserRepository;
import com.clicknsweet.clicknsweet.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User createUser(User newUser) {
        // Validación de email duplicado
        if (existsByEmail(newUser.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con el email: " + newUser.getEmail());
        }

        // Validación de username duplicado
        if (existsByUsername(newUser.getUserName())) {
            throw new RuntimeException("Ya existe un usuario con el nombre de usuario: " + newUser.getUserName());
        }

        // Validación de rol
        if (newUser.getRole() == null || newUser.getRole().getId() == null) {
            throw new RuntimeException("El rol es obligatorio");
        }

        Role role = roleRepository.findById(newUser.getRole().getId())
                .orElseThrow(() -> new RuntimeException("No se encontró el rol con ID: " + newUser.getRole().getId()));

        newUser.setRole(role); // Asignamos el rol gestionado por JPA

        return userRepository.save(newUser);
    }
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByUsername(String userName) {
        return userRepository.findByUserName(userName);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No user found with ID: " + id));
    }

    public void deleteById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("Cannot delete. No user found with id: " + id);
        }
    }

    public User updateUser(User user, Long id) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setFirstName(user.getFirstName());
                    existingUser.setLastName(user.getLastName());
                    existingUser.setUserName(user.getUserName());
                    existingUser.setEmail(user.getEmail());
                    existingUser.setPassword(user.getPassword());
                    existingUser.setPhone(user.getPhone());
                    existingUser.setRole(user.getRole());
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new UserNotFoundException("Cannot update. No user found with id: " + id));
    }

    public List<User> findByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName);
    }

    public List<User> findByFirstNameAndLastName(String firstName, String lastName) {
        return userRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    public User findByPhone(String phone) {
        return userRepository.findByPhone(phone);
    }

    public List<User> findByRoleId(Integer roleId) {
        return userRepository.findByRole_Id(roleId);
    }

    public User updateLastLogin(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setLast_login(LocalDateTime.now());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new UserNotFoundException("Cannot update login. No user found with ID: " + id));
    }
    public Role findRoleById(Integer id) {
        return roleRepository.findById(id.intValue()).orElse(null);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUserName(username);
    }

    public long countByRoleId(Integer roleId) {
        return userRepository.countByRole_Id(roleId);
    }

    public List<User> findActiveUsers(int days) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(days);
        return userRepository.findByLastLoginAfter(cutoffDate);
    }
}