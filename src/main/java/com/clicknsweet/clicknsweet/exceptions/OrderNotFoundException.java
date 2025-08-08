package com.clicknsweet.clicknsweet.exceptions;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Integer id) {
        super("No existe orden con id: " + id);
    }
}
