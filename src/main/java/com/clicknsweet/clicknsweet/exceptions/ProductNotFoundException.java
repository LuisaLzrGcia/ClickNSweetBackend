package com.clicknsweet.clicknsweet.exceptions;

public class ProductNotFoundException  extends RuntimeException{
    public ProductNotFoundException(Long id) {
        super("No existe producto con id "+id);
    }
}
