package com.clicknsweet.clicknsweet.controller;


import com.clicknsweet.clicknsweet.dto.OrderDTO;
import com.clicknsweet.clicknsweet.exceptions.OrderNotFoundException;
import com.clicknsweet.clicknsweet.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/clicknsweet/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        List<OrderDTO> orders = orderService.readOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Integer id) {
        OrderDTO order = orderService.findById(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDTO) {
        if (orderDTO.getOrderLines() == null || orderDTO.getOrderLines().isEmpty()) {
            return ResponseEntity.badRequest().body("La orden debe tener al menos un producto.");
        }

        OrderDTO creado = orderService.createOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Integer id, @RequestBody OrderDTO order) {
        OrderDTO actualizado = orderService.updateOrder(id, order);
        return ResponseEntity.ok(actualizado);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<OrderDTO> deleteOrder(@PathVariable Integer id) {
        OrderDTO eliminado = orderService.deleteOrder(id);
        return ResponseEntity.ok(eliminado);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByUserId(@PathVariable Integer userId) {
        List<OrderDTO> orders = orderService.getOrdersByUserId(userId);
        if (orders.isEmpty()) {
            throw new OrderNotFoundException(userId);
        }
        return ResponseEntity.ok(orders);
    }
}
