package com.clicknsweet.clicknsweet.exceptions;

public class CartItemNotFoundException extends RuntimeException {
    public CartItemNotFoundException(Integer id) {
        super("No se encontró el item del carrito " + id);
    }
}

