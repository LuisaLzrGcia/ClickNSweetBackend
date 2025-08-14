package com.clicknsweet.clicknsweet.controller;

import com.clicknsweet.clicknsweet.model.User;
import com.clicknsweet.clicknsweet.repository.RoleRepository;
import com.clicknsweet.clicknsweet.service.UserService;
import com.clicknsweet.clicknsweet.exceptions.UserNotFoundException;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.clicknsweet.clicknsweet.model.Role;





import java.util.List;

@RestController
@RequestMapping("/api/v1/clicknsweet")
public class UserController {

    private final UserService userService;

    @Autowired
    private RoleRepository roleRepository;

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
        try {
            System.out.println("=== DEBUGGING CREATE USER ===");
            System.out.println("Usuario completo recibido: " + newUser);
            System.out.println("FirstName: '" + newUser.getFirstName() + "'");
            System.out.println("LastName: '" + newUser.getLastName() + "'");
            System.out.println("UserName: '" + newUser.getUserName() + "'");
            System.out.println("Email: '" + newUser.getEmail() + "'");
            System.out.println("Password: '" + newUser.getPassword() + "'");
            System.out.println("Phone: '" + newUser.getPhone() + "'");
            System.out.println("Role: " + newUser.getRole());

            // Verificar si algún campo obligatorio es null
            if (newUser.getFirstName() == null) {
                System.out.println("ERROR: firstName es null!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            if (newUser.getLastName() == null) {
                System.out.println("ERROR: lastName es null!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            if (newUser.getUserName() == null) {
                System.out.println("ERROR: userName es null!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

            // Resto de validaciones...
            User existingUserByEmail = userService.findByEmail(newUser.getEmail());
            if (existingUserByEmail != null) {
                System.out.println("Email ya existe");
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            User existingUserByUsername = userService.findByUsername(newUser.getUserName());
            if (existingUserByUsername != null) {
                System.out.println("Username ya existe");
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }

            // Manejar el rol
            if (newUser.getRole() == null) {
                System.out.println("Creando rol por defecto...");
                Role defaultRole = new Role();
                defaultRole.setId(1);
                defaultRole.setType("USER");
                roleRepository.save(defaultRole);
                newUser.setRole(defaultRole);
            } else {
                Role role = userService.findRoleById(newUser.getRole().getId());
                if (role == null) {
                    System.out.println("Rol no encontrado!");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
                newUser.setRole(role);
            }

            System.out.println("Antes de guardar - Usuario: " + newUser);
            User createdUser = userService.createUser(newUser);
            System.out.println("Usuario creado exitosamente!");

            return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);

        } catch (Exception e) {
            System.err.println("ERROR COMPLETO:");
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
}

