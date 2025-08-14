package com.clicknsweet.clicknsweet.dto;

import com.clicknsweet.clicknsweet.exceptions.AddressNotFoundException;
import com.clicknsweet.clicknsweet.exceptions.ProductNotFoundException;
import com.clicknsweet.clicknsweet.exceptions.UserNotFoundException;
import com.clicknsweet.clicknsweet.model.*;
import com.clicknsweet.clicknsweet.repository.AddressRepository;
import com.clicknsweet.clicknsweet.repository.ProductRepository;
import com.clicknsweet.clicknsweet.repository.UserRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderDTO toDTO(Order order) {
        if (order == null) return null;

        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUser().getId());
        dto.setShippingAddressId(order.getShippingAddress().getId());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setStatus(order.getStatus());
        dto.setShippingCarrier(order.getShippingCarrier());
        dto.setTrackingNumber(order.getTrackingNumber());
        dto.setCreatedAt(order.getCreatedAt());

        // Direccion completa para el front
        dto.setShippingAddress(toAddressDTO(order.getShippingAddress()));

        List<OrderLineDTO> orderLinesDTO = order.getOrderLines()
                .stream()
                .map(OrderMapper::toOrderLineDTO)
                .collect(Collectors.toList());

        dto.setOrderLines(orderLinesDTO);
        return dto;
    }

    private static AddressDTO toAddressDTO(Address a) {
        if (a == null) return null;
        AddressDTO dto = new AddressDTO();
        dto.setId(a.getId());
        dto.setAddress(a.getAddress());
        dto.setCity(a.getCity());
        dto.setCountry(a.getCountry());
        dto.setRegion(a.getRegion());
        dto.setTypeAddress(a.getTypeAddress());
        return dto;
    }

    private static ProductDTO toProductDTO(Product product) {
        if (product == null) return null;

        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getProductName());
        dto.setImage(product.getPicture());
        dto.setPrice(product.getPrice());
        return dto;
    }

    private static OrderLineDTO toOrderLineDTO(OrderLine orderLine) {
        if (orderLine == null) return null;

        OrderLineDTO dto = new OrderLineDTO();
        dto.setOrderLineId(orderLine.getId());
        dto.setProductId(orderLine.getProduct().getId());
        dto.setQuantity(orderLine.getQuantity());
        dto.setPrice(orderLine.getPrice());
        dto.setProduct(toProductDTO(orderLine.getProduct()));
        return dto;
    }

    public static Order toEntity(
            OrderDTO dto,
            ProductRepository productRepository,
            UserRepository userRepository,
            AddressRepository addressRepository
    ) {
        if (dto == null) return null;

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("Usuario no encontrado: " + dto.getUserId()));
        Address address = addressRepository.findById(dto.getShippingAddressId())
                .orElseThrow(() -> new AddressNotFoundException(dto.getShippingAddressId()));

        Order order = new Order();
        order.setId(dto.getId());
        order.setUser(user);
        order.setShippingAddress(address);
        order.setStatus(dto.getStatus());
        order.setTrackingNumber(dto.getTrackingNumber());
        order.setShippingCarrier(dto.getShippingCarrier());

        List<OrderLine> orderLines = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (OrderLineDTO lineDTO : dto.getOrderLines()) {
            Product product = productRepository.findById(lineDTO.getProductId())
                    .orElseThrow(() -> new ProductNotFoundException(lineDTO.getProductId()));

            OrderLine line = new OrderLine();
            line.setOrder(order);
            line.setProduct(product);
            line.setQuantity(lineDTO.getQuantity());
            line.setPrice(product.getPrice());

            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(lineDTO.getQuantity())));
            orderLines.add(line);
        }

        order.setOrderLines(orderLines);
        order.setTotalAmount(total);

        return order;
    }
}