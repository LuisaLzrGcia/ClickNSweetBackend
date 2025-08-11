package com.clicknsweet.clicknsweet.service;

import com.clicknsweet.clicknsweet.dto.OrderDTO;
import com.clicknsweet.clicknsweet.dto.OrderLineDTO;
import com.clicknsweet.clicknsweet.exceptions.AddressNotFoundException;
import com.clicknsweet.clicknsweet.exceptions.OrderNotFoundException;
import com.clicknsweet.clicknsweet.exceptions.ProductNotFoundException;
import com.clicknsweet.clicknsweet.model.*;
import com.clicknsweet.clicknsweet.repository.AddressRepository;
import com.clicknsweet.clicknsweet.repository.OrderRepository;
import com.clicknsweet.clicknsweet.repository.ProductRepository;
import com.clicknsweet.clicknsweet.repository.UserRepository;
import com.clicknsweet.clicknsweet.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository,
                        AddressRepository addressRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    public Order createOrder(OrderDTO dto) {
        User user = userRepository.findById(Long.valueOf(dto.getUserId()))
                .orElseThrow(() -> new UserNotFoundException("No existe usuario con id "+ dto.getUserId()));

        Address address = addressRepository.findById(dto.getShippingAddressId())
                .orElseThrow(() -> new AddressNotFoundException(dto.getShippingAddressId()));

        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(address);
        order.setStatus(dto.getStatus());
        order.setTrackingNumber(dto.getTrackingNumber());
        order.setShippingCarrier(dto.getShippingCarrier());

        List<OrderLine> orderLine = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderLineDTO orderLineDTO : dto.getOrderLines()) {
            Long productId = Long.parseLong(String.valueOf(orderLineDTO.getProductId()));
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException(productId));

            OrderLine line = new OrderLine();
            line.setProduct(product);
            line.setQuantity(orderLineDTO.getQuantity());
            line.setPrice(product.getPrice());
            line.setOrder(order);

             total = total.add(product.getPrice().multiply(BigDecimal.valueOf(orderLineDTO.getQuantity())));
            orderLine.add(line);

        }
        order.setOrderLines(orderLine);
        order.setTotalAmount(total);
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
