package com.clicknsweet.clicknsweet.exceptions;

public class ProductAlreadyExistsException  extends RuntimeException{
    public ProductAlreadyExistsException(String name) {
        super("Ya existe un producto con el nombre: " + name);
    }
}
