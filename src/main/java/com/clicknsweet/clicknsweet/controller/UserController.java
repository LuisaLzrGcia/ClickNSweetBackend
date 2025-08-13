package com.clicknsweet.clicknsweet.controller;

import com.clicknsweet.clicknsweet.model.User;
import com.clicknsweet.clicknsweet.service.UserService;
import com.clicknsweet.clicknsweet.exceptions.UserNotFoundException;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




import java.util.List;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/clicknsweet")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        try {
            User user = userService.findById(id);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        User userByEmail = userService.findByEmail(email);
        if (userByEmail == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userByEmail);
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User userByUsername = userService.findByUsername(username);
        if (userByUsername == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userByUsername);
    }

    @GetMapping("/search/firstname/{firstName}")
    public ResponseEntity<List<User>> getUsersByFirstName(@PathVariable String firstName) {
        List<User> users = userService.findByFirstName(firstName);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/phone/{phone}")
    public ResponseEntity<User> getUserByPhone(@PathVariable String phone) {
        User userByPhone = userService.findByPhone(phone);
        if (userByPhone == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userByPhone);
    }

    @PostMapping("/create-user")
    public ResponseEntity<User> createUser(@RequestBody User newUser) {
        User existingUserByEmail = userService.findByEmail(newUser.getEmail());
        if (existingUserByEmail != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 - Email ya existe
        }

        User existingUserByUsername = userService.findByUsername(newUser.getUser_name());
        if (existingUserByUsername != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409 - Username ya existe
        }

        User createdUser = userService.createUser(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser); // 201
    }

    @PutMapping("/update-user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userToUpdate) {
        try {
            User updatedUser = userService.updateUser(userToUpdate, id);
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404
        }
    }

    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteById(id);
            return ResponseEntity.ok().build(); // 200
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404
        }
    }
    @PatchMapping("/update-user/{id}")
    public ResponseEntity<User> patchUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User user = userService.patchUser(id, updatedUser);
        return ResponseEntity.ok(user);
    }
}

