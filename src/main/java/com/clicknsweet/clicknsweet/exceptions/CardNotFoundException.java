package com.clicknsweet.clicknsweet.exceptions;

public class CardNotFoundException extends RuntimeException {
    public CardNotFoundException(Integer id) {
        super("No se encontró la tarjeta " + id);
    }
}
