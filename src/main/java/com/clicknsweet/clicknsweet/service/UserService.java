package com.clicknsweet.clicknsweet.service;

import com.clicknsweet.clicknsweet.model.User;
import com.clicknsweet.clicknsweet.repository.UserRepository;
import com.clicknsweet.clicknsweet.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User createUser(User newUser) {
        return userRepository.save(newUser);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("No user found with ID: " + id));
    }

    public void deleteById(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new UserNotFoundException("Cannot delete. No user found with ID: " + id);
        }
    }

    public User updateUser(User user, Long id) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setFirst_name(user.getFirst_name());
                    existingUser.setLast_name(user.getLast_name());
                    existingUser.setUser_name(user.getUser_name());
                    existingUser.setEmail(user.getEmail());
                    existingUser.setPassword(user.getPassword());
                    existingUser.setPhone(user.getPhone());
                    existingUser.setRole(user.getRole());
                    return userRepository.save(existingUser);
                })
                .orElseThrow(() -> new UserNotFoundException("Cannot update. No user found with ID: " + id));
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
        return userRepository.findByRoleId(roleId);
    }

    public User updateLastLogin(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setLast_login(LocalDateTime.now());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new UserNotFoundException("Cannot update login. No user found with ID: " + id));
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public long countByRoleId(Integer roleId) {
        return userRepository.countByRoleId(roleId);
    }

    public List<User> findActiveUsers(int days) {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(days);
        return userRepository.findByLastLoginAfter(cutoffDate);
    }
}