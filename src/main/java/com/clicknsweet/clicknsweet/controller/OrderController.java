package com.clicknsweet.clicknsweet.controller;


import com.clicknsweet.clicknsweet.dto.OrderDTO;
import com.clicknsweet.clicknsweet.exceptions.OrderNotFoundException;
import com.clicknsweet.clicknsweet.model.Order;
import com.clicknsweet.clicknsweet.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/clicknsweet/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getAllOrders() {
        List<Order> orders = orderService.readOrders();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Integer id) {
        Order order = orderService.findById(id);
        return ResponseEntity.ok(order);
    }



    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO orderDTO) {
        if (orderDTO.getOrderLines() == null || orderDTO.getOrderLines().isEmpty()) {
            return ResponseEntity.badRequest().body("La orden debe tener al menos un producto.");
        }

        Order creado = orderService.createOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable Integer id, @RequestBody Order order) {
        Order actualizado = orderService.updateOrder(id, order);
        return ResponseEntity.ok(actualizado);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable Integer id) {
        Order eliminado = orderService.deleteOrder(id);
        return ResponseEntity.ok(eliminado);
    }
}
