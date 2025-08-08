package com.clicknsweet.clicknsweet.exceptions;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException(Integer id) {
        super("No se encuentra la categoría " + id);
    }
}
