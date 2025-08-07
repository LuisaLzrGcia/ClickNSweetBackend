package com.clicknsweet.clicknsweet.exceptions;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException(Integer id) {
      super("No se encuentra la dirección id " + id);
    }
}
