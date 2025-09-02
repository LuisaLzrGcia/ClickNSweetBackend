package com.clicknsweet.clicknsweet.controller;


import com.clicknsweet.clicknsweet.model.Address;
import com.clicknsweet.clicknsweet.service.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/clicknsweet")
@Tag(name = "Address Controller", description = "Gestión de direcciones: creación, actualización, eliminación y obtención de direcciones.")
public class AddressController {
    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    // Mapear getAddress()

    @Operation(
            summary = "Obtener todas las direcciones",
            description = "Recupera una lista de todas las direcciones almacenadas en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de direcciones recuperada exitosamente",content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Address.class),
                    examples = {
                            @ExampleObject(
                                    name = "Ejemplo de respuesta",
                                    summary = "Lista de direcciones",
                                    value = "[\n" +
                                            "  {\n" +
                                            "    \"id\": 1,\n" +
                                            "    \"street\": \"Calle Falsa 123\",\n" +
                                            "    \"city\": \"Springfield\",\n" +
                                            "    \"state\": \"IL\",\n" +
                                            "    \"zipCode\": \"62704\",\n" +
                                            "    \"country\": \"USA\",\n" +
                                            "    \"userId\": 42\n" +
                                            "  },\n" +
                                            "  {\n" +
                                            "    \"id\": 2,\n" +
                                            "    \"street\": \"Avenida Siempre Viva 742\",\n" +
                                            "    \"city\": \"Shelbyville\",\n" +
                                            "    \"state\": \"IL\",\n" +
                                            "    \"zipCode\": \"62565\",\n" +
                                            "    \"country\": \"USA\",\n" +
                                            "    \"userId\": 43\n" +
                                            "  }\n" +
                                            "]"
                            )
                    }
            )),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = Address.class),
                    examples = {
                            @ExampleObject(
                                    name = "Ejemplo de error",
                                    summary = "Error interno del servidor",
                                    value = "{\n" +
                                            "  \"timestamp\": \"2024-10-01T12:00:00Z\",\n" +
                                            "  \"status\": 500,\n" +
                                            "  \"error\": \"Internal Server Error\",\n" +
                                            "  \"message\": \"Se produjo un error inesperado.\",\n" +
                                            "  \"path\": \"/api/v1/clicknsweet/address\"\n" +
                                            "}"
                            )
                    }
            ))
    })
    @GetMapping("/address")
    public ResponseEntity<List<Address>> getAllAddresses() {
        return ResponseEntity.ok(addressService.getAddress());
    }

    // Mapear por id
    @Operation(
            summary = "Obtener dirección por ID",
            description = "Recupera una dirección específica utilizando su ID único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dirección recuperada exitosamente",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Address.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Ejemplo de respuesta",
                                            summary = "Dirección encontrada",
                                            value = "{\n" +
                                                    "  \"id\": 1,\n" +
                                                    "  \"street\": \"Calle Falsa 123\",\n" +
                                                    "  \"city\": \"Springfield\",\n" +
                                                    "  \"state\": \"IL\",\n" +
                                                    "  \"zipCode\": \"62704\",\n" +
                                                    "  \"country\": \"USA\",\n" +
                                                    "  \"userId\": 42\n" +
                                                    "}"
                                    )
                            }
                    )),
            @ApiResponse(responseCode = "404", description = "Dirección no encontrada",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Address.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Ejemplo de error",
                                            summary = "Dirección no encontrada",
                                            value = "{\n" +
                                                    "  \"timestamp\": \"2024-10-01T12:00:00Z\",\n" +
                                                    "  \"status\": 404,\n" +
                                                    "  \"error\": \"Not Found\",\n" +
                                                    "  \"message\": \"Dirección con ID 1 no encontrada.\",\n" +
                                                    "  \"path\": \"/api/v1/clicknsweet/address/1\"\n" +
                                                    "}"
                                    )
                            }
                    )),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Address.class),
                            examples = {
                                    @ExampleObject(
                                            name = "Ejemplo de error",
                                            summary = "Error interno del servidor",
                                            value = "{\n" +
                                                    "  \"timestamp\": \"2024-10-01T12:00:00Z\",\n" +
                                                    "  \"status\": 500,\n" +
                                                    "  \"error\": \"Internal Server Error\",\n" +
                                                    "  \"message\": \"Se produjo un error inesperado.\",\n" +
                                                    "  \"path\": \"/api/v1/clicknsweet/address/1\"\n" +
                                                    "}"
                                    )
                            }
                    ))
    })
    @GetMapping("/address/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Integer id) {
        Address address = addressService.getAddressById(id);
        return ResponseEntity.ok(address);
    }

    // Mapear createAddress
    @Operation(
            summary = "Crear una nueva dirección",
            description = "Agrega una nueva dirección al sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Dirección creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PostMapping("/address")
    public ResponseEntity<Address> createAddress(@RequestBody Address address) {
        Address created = addressService.createAddress(address);
        return ResponseEntity.created(URI.create("/api/v1/clicknsweet/address/" + created.getId()))
                .body(created);

    }


    @Operation(
            summary = "Obtener direcciones por ID de usuario",
            description = "Recupera todas las direcciones asociadas a un usuario específico utilizando su ID de usuario."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de direcciones recuperada exitosamente"),
            @ApiResponse(responseCode = "204", description = "No se encontraron direcciones para el usuario especificado"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor"),

    })
    @GetMapping("/address/user/{userId}")
    public ResponseEntity<List<Address>> getAddressesByUser(@PathVariable Long userId) {
        List<Address> address = addressService.getAddressByUserId(userId);
        if (address.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(address);
    }

    // Mapear updateAddress
    @Operation(
            summary = "Actualizar una dirección existente",
            description = "Modifica los detalles de una dirección existente utilizando su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dirección actualizada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida"),
            @ApiResponse(responseCode = "404", description = "Dirección no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @PutMapping("/address/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Integer id, @RequestBody Address address) {
        Address updated = addressService.updateAddress(address, id);
        return ResponseEntity.ok(updated);
    }

    // Mapear deleteAddress
    @Operation(
            summary = "Eliminar una dirección",
            description = "Elimina una dirección específica utilizando su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Dirección eliminada exitosamente"),
            @ApiResponse(responseCode = "404", description = "Dirección no encontrada"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
    })
    @DeleteMapping("/address/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Integer id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }



}
