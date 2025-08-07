package com.clicknsweet.clicknsweet.service;

import com.clicknsweet.clicknsweet.exceptions.OrderNotFoundException;
import com.clicknsweet.clicknsweet.model.Order;
import com.clicknsweet.clicknsweet.model.OrderLine;
import com.clicknsweet.clicknsweet.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public Order createOrder(Order order) {
        BigDecimal total = BigDecimal.ZERO;
        for (OrderLine line : order.getOrderLines()) {
            BigDecimal quantity = BigDecimal.valueOf(line.getQuantity());
            BigDecimal subtotal = line.getPrice().multiply(quantity);
            total = total.add(subtotal);
            line.setOrder(order);
        }
        order.setTotalAmount(total);
        order.setStatus(order.getStatus() != null ? order.getStatus() : "Pendiente");
        return orderRepository.save(order);
    }



    public List<Order> readOrders(){
    List<Order> orders = orderRepository.findAll();
    return orders;
    }

    public Order findById(Integer id){
    return orderRepository.findById(id)
            .orElseThrow(()-> new OrderNotFoundException(id));
    }

    public Order updateOrder(Integer id, Order order) {
        return orderRepository.findById(id)
                .map(existente -> {
                    existente.setStatus(order.getStatus());
                    existente.setTrackingNumber(order.getTrackingNumber());
                    existente.setShippingCarrier(order.getShippingCarrier());

                    order.getOrderLines().forEach(line -> line.setOrder(existente));

                    existente.setOrderLines(order.getOrderLines());

                    BigDecimal total = existente.getOrderLines().stream()
                            .map(l -> l.getPrice().multiply(BigDecimal.valueOf(l.getQuantity())))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    existente.setTotalAmount(total);

                    return orderRepository.save(existente);
                })
                .orElseThrow(() -> new OrderNotFoundException(id));
    }


    public Order deleteOrder(Integer id) {
        Order order = findById(id);
        orderRepository.delete(order);
        return order;
    }
}
