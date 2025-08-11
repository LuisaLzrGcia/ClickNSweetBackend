package com.clicknsweet.clicknsweet.exceptions;

public class CartNotFoundException extends RuntimeException {
  public CartNotFoundException(Integer id) {
    super("No se encontró el carrito " + id);
  }
  public CartNotFoundException(String by) {
    super("No se encontró el carrito " + by);
  }
}

