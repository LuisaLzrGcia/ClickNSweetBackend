package com.clicknsweet.clicknsweet.service;

import com.clicknsweet.clicknsweet.dto.OrderDTO;
import com.clicknsweet.clicknsweet.dto.OrderLineDTO;
import com.clicknsweet.clicknsweet.dto.OrderMapper;
import com.clicknsweet.clicknsweet.exceptions.AddressNotFoundException;
import com.clicknsweet.clicknsweet.exceptions.OrderNotFoundException;
import com.clicknsweet.clicknsweet.exceptions.ProductNotFoundException;
import com.clicknsweet.clicknsweet.model.*;
import com.clicknsweet.clicknsweet.repository.AddressRepository;
import com.clicknsweet.clicknsweet.repository.OrderRepository;
import com.clicknsweet.clicknsweet.repository.ProductRepository;
import com.clicknsweet.clicknsweet.repository.UserRepository;
import com.clicknsweet.clicknsweet.exceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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

    public OrderDTO createOrder(OrderDTO dto) {
        // 1️⃣ Obtener usuario
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("No existe usuario con id " + dto.getUserId()));

        // 2️⃣ Obtener dirección de envío
        Address address = addressRepository.findById(dto.getShippingAddressId())
                .orElseThrow(() -> new AddressNotFoundException(dto.getShippingAddressId()));

        // 3️⃣ Crear nueva orden
        Order order = new Order();
        order.setUser(user);
        order.setShippingAddress(address);
        order.setStatus(dto.getStatus());
        order.setTrackingNumber(dto.getTrackingNumber());
        order.setShippingCarrier(dto.getShippingCarrier());

        List<OrderLine> orderLines = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        // 4️⃣ Procesar cada línea del pedido
        for (OrderLineDTO orderLineDTO : dto.getOrderLines()) {
            // Obtener producto desde ProductDTO
            Long productId = orderLineDTO.getProduct().getId();
            Product product = productRepository.findById(productId)
                    .orElseThrow(() -> new ProductNotFoundException(productId));

            OrderLine line = new OrderLine();
            line.setProduct(product);
            line.setQuantity(orderLineDTO.getQuantity());
            line.setPrice(product.getPrice());
            line.setOrder(order);

            // Calcular total del pedido
            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(orderLineDTO.getQuantity())));

            orderLines.add(line);
        }

        // 5️⃣ Asignar líneas y total a la orden
        order.setOrderLines(orderLines);
        order.setTotalAmount(total);

        // 6️⃣ Guardar orden en la base de datos
        Order savedOrder = orderRepository.save(order);

        // 7️⃣ Retornar DTO con ProductDTO completo
        return OrderMapper.toDTO(savedOrder);
    }




    public List<OrderDTO> readOrders(){
        List<Order> orders = orderRepository.findAll();
        return orders.stream()
                .map(OrderMapper::toDTO)
                .toList();
    }

    public OrderDTO findById(Integer id){
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        return OrderMapper.toDTO(order);
    }

    public OrderDTO updateOrder(Integer id, OrderDTO dto) {
        return orderRepository.findById(id)
                .map(existente -> {
                    List<OrderLine> nuevasLineas = dto.getOrderLines().stream()
                            .map(orderLineDTO -> {
                                OrderLine line = new OrderLine();
                                line.setQuantity(orderLineDTO.getQuantity());

                                // Obtener productId desde ProductDTO
                                Long productId = orderLineDTO.getProduct().getId();
                                Product product = productRepository.findById(productId)
                                        .orElseThrow(() -> new ProductNotFoundException(productId));

                                line.setProduct(product);
                                line.setPrice(product.getPrice());
                                line.setOrder(existente);
                                return line;
                            })
                            .collect(Collectors.toList());

                    // Reemplazar líneas existentes
                    existente.getOrderLines().clear();
                    existente.getOrderLines().addAll(nuevasLineas);

                    // Recalcular total
                    BigDecimal total = existente.getOrderLines().stream()
                            .map(line -> line.getPrice().multiply(BigDecimal.valueOf(line.getQuantity())))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    existente.setTotalAmount(total);

                    // Actualizar datos de la orden
                    existente.setStatus(dto.getStatus());
                    existente.setTrackingNumber(dto.getTrackingNumber());
                    existente.setShippingCarrier(dto.getShippingCarrier());

                    Order updated = orderRepository.save(existente);
                    return OrderMapper.toDTO(updated);
                })
                .orElseThrow(() -> new OrderNotFoundException(id));
    }



    public OrderDTO deleteOrder(Integer id) {
        OrderDTO order = findById(id);
        orderRepository.deleteById(id);
        return order;
    }
    public List<OrderDTO> getOrdersByUserId(Integer userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(OrderMapper::toDTO)
                .collect(Collectors.toList());
    }
}
