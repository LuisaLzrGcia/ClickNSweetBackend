package com.clicknsweet.clicknsweet.controller;

import com.clicknsweet.clicknsweet.model.User;
import com.clicknsweet.clicknsweet.repository.RoleRepository;
import com.clicknsweet.clicknsweet.service.UserService;
import com.clicknsweet.clicknsweet.exceptions.UserNotFoundException;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.clicknsweet.clicknsweet.model.Role;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/clicknsweet")
@Tag(name = "User Controller", description = "Gestión de usuarios: registro, login, actualización, búsqueda y eliminación.")
public class UserController {

    private final UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @Operation(
            summary = "Obtener todos los usuarios",
            description = "Devuelve una lista de todos los usuarios registrados en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/users")
    public List<User> getUsers(){
        return userService.getUsers();
    }

    @Operation(
            summary = "Obtener usuario por ID",
            description = "Devuelve los detalles de un usuario específico basado en su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        try {
            User user = userService.findById(id);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(
            summary = "Obtener usuario por email",
            description = "Devuelve los detalles de un usuario específico basado en su email."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        User userByEmail = userService.findByEmail(email.toLowerCase().trim());

        if (userByEmail == null) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Usuario no encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        return ResponseEntity.ok(userByEmail);
    }

    @Operation(
            summary = "Obtener usuario por nombre de usuario",
            description = "Devuelve los detalles de un usuario específico basado en su nombre de usuario."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User userByUsername = userService.findByUsername(username);
        if (userByUsername == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userByUsername);
    }
    @Operation(
            summary = "Buscar usuarios por nombre",
            description = "Devuelve una lista de usuarios cuyos nombres coinciden con el nombre proporcionado."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida exitosamente"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/search/firstname/{firstName}")
    public ResponseEntity<List<User>> getUsersByFirstName(@PathVariable String firstName) {
        List<User> users = userService.findByFirstName(firstName);
        return ResponseEntity.ok(users);
    }

    @Operation(
            summary = "Obtener usuario por teléfono",
            description = "Devuelve los detalles de un usuario específico basado en su número de teléfono."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario encontrado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @GetMapping("/phone/{phone}")
    public ResponseEntity<User> getUserByPhone(@PathVariable String phone) {
        User userByPhone = userService.findByPhone(phone);
        if (userByPhone == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userByPhone);
    }

    @Operation(
            summary = "Crear un nuevo usuario",
            description = "Registra un nuevo usuario en el sistema."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuario creado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "409", description = "Conflicto: email o username ya existen"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
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

    @Operation(
            summary = "Login de usuario",
            description = "Autentica a un usuario con su email y contraseña."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login exitoso"),
            @ApiResponse(responseCode = "401", description = "Contraseña incorrecta"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String password = credentials.get("password");

        User user = userService.findByEmail(email.toLowerCase().trim());
        if (user == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Usuario no encontrado"));
        }

        if (!user.getPassword().equals(password)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Contraseña incorrecta"));
        }

        return ResponseEntity.ok(user);
    }

    @Operation(
            summary = "Actualizar usuario",
            description = "Actualiza los detalles de un usuario existente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/update-user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userToUpdate) {
        try {
            User updatedUser = userService.updateUser(userToUpdate, id);
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404
        }
    }

    @Operation(
            summary = "Eliminar usuario",
            description = "Elimina un usuario específico basado en su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario eliminado exitosamente"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/delete-user/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteById(id);
            return ResponseEntity.ok().build(); // 200
        } catch (UserNotFoundException e) {
            return ResponseEntity.notFound().build(); // 404
        }
    }

    @Operation(
            summary = "Actualizar parcialmente un usuario",
            description = "Actualiza parcialmente los detalles de un usuario existente."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "404", description = "Usuario no encontrado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PatchMapping("/update-user/{id}")
    public ResponseEntity<User> patchUser(@PathVariable Long id, @RequestBody User updatedUser) {
        User user = userService.patchUser(id, updatedUser);
        return ResponseEntity.ok(user);
    }
}

