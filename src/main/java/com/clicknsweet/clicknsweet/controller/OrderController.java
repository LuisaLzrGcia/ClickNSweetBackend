package com.clicknsweet.clicknsweet.controller;


import com.clicknsweet.clicknsweet.dto.OrderDTO;
import com.clicknsweet.clicknsweet.exceptions.OrderNotFoundException;
import com.clicknsweet.clicknsweet.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/clicknsweet/orders")
@Tag(name = "Order Controller", description = "Gestion de ordenes: creacion, actualizacion, eliminacion y obtencion de ordenes.")
public class OrderController {
    @Autowired
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(
            summary = "Obtener todas las ordenes",
            description = "Recupera una lista de todas las ordenes almacenadas en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ordenes recuperada exitosamente", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OrderDTO.class),
                    examples = {
                            @ExampleObject(
                                    name = "Ejemplo de respuesta exitosa",
                                    summary = "Respuesta JSON de una lista de ordenes",
                                    value = "[\n" +
                                            "  {\n" +
                                            "    \"id\": 1,\n" +
                                            "    \"userId\": 123,\n" +
                                            "    \"orderDate\": \"2023-10-01T12:34:56\",\n" +
                                            "    \"status\": \"COMPLETED\",\n" +
                                            "    \"totalAmount\": 99.99,\n" +
                                            "    \"orderLines\": [\n" +
                                            "      {\n" +
                                            "        \"productId\": 1,\n" +
                                            "        \"quantity\": 2,\n" +
                                            "        \"price\": 49.99\n" +
                                            "      }\n" +
                                            "    ]\n" +
                                            "  }\n" +
                                            "]"
                            )
                    }
            )),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{ \"timestamp\": \"2023-10-01T12:34:56\", \"status\": 500, \"error\": \"Internal Server Error\", \"message\": \"Error al recuperar las ordenes.\", \"path\": \"/api/v1/clicknsweet/orders\" }")
                    )
            )
    })
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.readOrders();
        return ResponseEntity.ok(orders);
    }

    @Operation(
            summary = "Obtener orden por ID",
            description = "Recupera una orden específica utilizando su ID único."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orden recuperada exitosamente", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OrderDTO.class),
                    examples = {
                            @ExampleObject(
                                    name = "Ejemplo de respuesta exitosa",
                                    summary = "Respuesta JSON de una orden",
                                    value = "{\n" +
                                            "  \"id\": 1,\n" +
                                            "  \"userId\": 123,\n" +
                                            "  \"orderDate\": \"2023-10-01T12:34:56\",\n" +
                                            "  \"status\": \"COMPLETED\",\n" +
                                            "  \"totalAmount\": 99.99,\n" +
                                            "  \"orderLines\": [\n" +
                                            "    {\n" +
                                            "      \"productId\": 1,\n" +
                                            "      \"quantity\": 2,\n" +
                                            "      \"price\": 49.99\n" +
                                            "    }\n" +
                                            "  ]\n" +
                                            "}"
                            )
                    }
            )),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{ \"timestamp\": \"2023-10-01T12:34:56\", \"status\": 404, \"error\": \"Not Found\", \"message\": \"Order con ID 1 no encontrada.\", \"path\": \"/api/v1/clicknsweet/orders/1\" }")
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{ \"timestamp\": \"2023-10-01T12:34:56\", \"status\": 500, \"error\": \"Internal Server Error\", \"message\": \"Error al recuperar la orden.\", \"path\": \"/api/v1/clicknsweet/orders/1\" }")
                    )
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Integer id) {
        OrderDTO order = orderService.findById(id);
        return ResponseEntity.ok(order);
    }

    @Operation(
            summary = "Crear una nueva orden",
            description = "Crea una nueva orden en el sistema."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Orden creada exitosamente", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OrderDTO.class),
                    examples = {
                            @ExampleObject(
                                    name = "Ejemplo de respuesta exitosa",
                                    summary = "Respuesta JSON de una orden creada",
                                    value = "{\n" +
                                            "  \"id\": 1,\n" +
                                            "  \"userId\": 123,\n" +
                                            "  \"orderDate\": \"2023-10-01T12:34:56\",\n" +
                                            "  \"status\": \"PENDING\",\n" +
                                            "  \"totalAmount\": 99.99,\n" +
                                            "  \"orderLines\": [\n" +
                                            "    {\n" +
                                            "      \"productId\": 1,\n" +
                                            "      \"quantity\": 2,\n" +
                                            "      \"price\": 49.99\n" +
                                            "    }\n" +
                                            "  ]\n" +
                                            "}"
                            )
                    }
            )),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{ \"timestamp\": \"2023-10-01T12:34:56\", \"status\": 400, \"error\": \"Bad Request\", \"message\": \"La orden debe tener al menos un producto.\", \"path\": \"/api/v1/clicknsweet/orders\" }")
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{ \"timestamp\": \"2023-10-01T12:34:56\", \"status\": 500, \"error\": \"Internal Server Error\", \"message\": \"Error al crear la orden.\", \"path\": \"/api/v1/clicknsweet/orders\" }")
                    )
            )
    })
    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDTO) {
        if (orderDTO.getOrderLines() == null || orderDTO.getOrderLines().isEmpty()) {
            return ResponseEntity.badRequest().body("La orden debe tener al menos un producto.");
        }

        OrderDTO creado = orderService.createOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(
            summary = "Actualizar una orden existente",
            description = "Actualiza los detalles de una orden existente utilizando su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orden actualizada exitosamente", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OrderDTO.class),
                    examples = {
                            @ExampleObject(
                                    name = "Ejemplo de respuesta exitosa",
                                    summary = "Respuesta JSON de una orden actualizada",
                                    value = "{\n" +
                                            "  \"id\": 1,\n" +
                                            "  \"userId\": 123,\n" +
                                            "  \"orderDate\": \"2023-10-01T12:34:56\",\n" +
                                            "  \"status\": \"SHIPPED\",\n" +
                                            "  \"totalAmount\": 99.99,\n" +
                                            "  \"orderLines\": [\n" +
                                            "    {\n" +
                                            "      \"productId\": 1,\n" +
                                            "      \"quantity\": 2,\n" +
                                            "      \"price\": 49.99\n" +
                                            "    }\n" +
                                            "  ]\n" +
                                            "}"
                            )
                    }
            )),
            @ApiResponse(responseCode = "400", description = "Solicitud inválida",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{ \"timestamp\": \"2023-10-01T12:34:56\", \"status\": 400, \"error\": \"Bad Request\", \"message\": \"El estado de la orden no es válido.\", \"path\": \"/api/v1/clicknsweet/orders/1\" }")
                    )
            ),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{ \"timestamp\": \"2023-10-01T12:34:56\", \"status\": 404, \"error\": \"Not Found\", \"message\": \"Order con ID 1 no encontrada.\", \"path\": \"/api/v1/clicknsweet/orders/1\" }")
                    )
            ),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{ \"timestamp\": \"2023-10-01T12:34:56\", \"status\": 500, \"error\": \"Internal Server Error\", \"message\": \"Error al actualizar la orden.\", \"path\": \"/api/v1/clicknsweet/orders/1\" }")
                    )
            )
    })
    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Integer id, @RequestBody OrderDTO order) {
        OrderDTO actualizado = orderService.updateOrder(id, order);
        return ResponseEntity.ok(actualizado);
    }

    @Operation(
            summary = "Eliminar una orden",
            description = "Elimina una orden del sistema utilizando su ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orden eliminada exitosamente", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OrderDTO.class),
                    examples = {
                            @ExampleObject(
                                    name = "Ejemplo de respuesta exitosa",
                                    summary = "Respuesta JSON de una orden eliminada",
                                    value = "{\n" +
                                            "  \"id\": 1,\n" +
                                            "  \"userId\": 123,\n" +
                                            "  \"orderDate\": \"2023-10-01T12:34:56\",\n" +
                                            "  \"status\": \"CANCELLED\",\n" +
                                            "  \"totalAmount\": 99.99,\n" +
                                            "  \"orderLines\": [\n" +
                                            "    {\n" +
                                            "      \"productId\": 1,\n" +
                                            "      \"quantity\": 2,\n" +
                                            "      \"price\": 49.99\n" +
                                            "    }\n" +
                                            "  ]\n" +
                                            "}"
                            )
                    }
            )),
            @ApiResponse(responseCode = "404", description = "Orden no encontrada",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{ \"timestamp\": \"2023-10-01T12:34:56\", \"status\": 404, \"error\": \"Not Found\", \"message\": \"Order con ID 1 no encontrada.\", \"path\": \"/api/v1/clicknsweet/orders/1\" }")
                    )
            ),

            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{ \"timestamp\": \"2023-10-01T12:34:56\", \"status\": 500, \"error\": \"Internal Server Error\", \"message\": \"Error al eliminar la orden.\", \"path\": \"/api/v1/clicknsweet/orders/1\" }")
                    )
            )
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<OrderDTO> deleteOrder(@PathVariable Integer id) {
        OrderDTO eliminado = orderService.deleteOrder(id);
        return ResponseEntity.ok(eliminado);
    }

    @Operation(
            summary = "Obtener ordenes por ID de usuario",
            description = "Recupera una lista de ordenes asociadas a un ID de usuario específico."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ordenes recuperada exitosamente", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = OrderDTO.class),
                    examples = {
                            @ExampleObject(
                                    name = "Ejemplo de respuesta exitosa",
                                    summary = "Respuesta JSON de una lista de ordenes",
                                    value = "[\n" +
                                            "  {\n" +
                                            "    \"id\": 1,\n" +
                                            "    \"userId\": 123,\n" +
                                            "    \"orderDate\": \"2023-10-01T12:34:56\",\n" +
                                            "    \"status\": \"COMPLETED\",\n" +
                                            "    \"totalAmount\": 99.99,\n" +
                                            "    \"orderLines\": [\n" +
                                            "      {\n" +
                                            "        \"productId\": 1,\n" +
                                            "        \"quantity\": 2,\n" +
                                            "        \"price\": 49.99\n" +
                                            "      }\n" +
                                            "    ]\n" +
                                            "  }\n" +
                                            "]"
                            )
                    }
            )),
            @ApiResponse(responseCode = "204", description = "No se encontraron ordenes para el usuario especificado",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(example = ""),
                            examples = @ExampleObject(
                                    name = "Ejemplo de respuesta sin contenido",
                                    summary = "Respuesta JSON vacía cuando no hay ordenes",
                                    value = ""
                    )
            )),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(example = "{ \"timestamp\": \"2023-10-01T12:34:56\", \"status\": 500, \"error\": \"Internal Server Error\", \"message\": \"Error al recuperar las ordenes del usuario.\", \"path\": \"/api/v1/clicknsweet/orders/user/123\" }")
                    )
            )
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByUserId(@PathVariable Integer userId) {
        List<OrderDTO> orders = orderService.getOrdersByUserId(userId);
        if (orders.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orders);
    }
}
