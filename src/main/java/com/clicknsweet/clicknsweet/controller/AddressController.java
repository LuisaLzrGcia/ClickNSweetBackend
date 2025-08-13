package com.clicknsweet.clicknsweet.controller;


import com.clicknsweet.clicknsweet.model.Address;
import com.clicknsweet.clicknsweet.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/clicknsweet")
public class AddressController {
    private final AddressService addressService;

    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    // Mapear getAddress()
    @GetMapping("/address")
    public ResponseEntity<List<Address>> getAllAddresses() {
        return ResponseEntity.ok(addressService.getAddress());
    }

    // Mapear por id
    @GetMapping("/address/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Integer id) {
        Address address = addressService.getAddressById(id);
        return ResponseEntity.ok(address);
    }

    // Mapear createAddress
    @PostMapping("/address")
    public ResponseEntity<Address> createAddress(@RequestBody Address address) {
        Address created = addressService.createAddress(address);
        return ResponseEntity.created(URI.create("/api/v1/clicknsweet/address/" + created.getId()))
                .body(created);

    }

    @GetMapping("/address/user/{userId}")
    public ResponseEntity<List<Address>> getAddressesByUser(@PathVariable Long userId) {
        List<Address> address = addressService.getAddressByUserId(userId);
        if (address.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(address);
    }

    // Mapear updateAddress
    @PutMapping("/address/{id}")
    public ResponseEntity<Address> updateAddress(@PathVariable Integer id, @RequestBody Address address) {
        Address updated = addressService.updateAddress(address, id);
        return ResponseEntity.ok(updated);
    }

    // Mapear deleteAddress
    @DeleteMapping("/address/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Integer id) {
        addressService.deleteAddress(id);
        return ResponseEntity.noContent().build();
    }



}
