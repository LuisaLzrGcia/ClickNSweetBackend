package com.clicknsweet.clicknsweet.service;

import com.clicknsweet.clicknsweet.exceptions.AddressNotFoundException;
import com.clicknsweet.clicknsweet.model.Address;
import com.clicknsweet.clicknsweet.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    // Métodoo para recuperar todos los Address
    public List<Address> getAddress() {
        return addressRepository.findAll();
    }

    // Métodoo para crear un nuevo address
    public Address createAddress(Address newAddress) {
        return addressRepository.save(newAddress);
    }

    public Address getAddressById(Integer id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new AddressNotFoundException(id));
    }

    public List<Address> getAddressByUserId(Long userId) {
        return addressRepository.findByUserId(userId);
    }


    public void deleteAddress(Integer id) {
        if (addressRepository.existsById(id)) {
            addressRepository.deleteById(id);
        } else {
            throw new AddressNotFoundException(id);
        }
    }

    // Métodoo para actualizar address
    public Address updateAddress(Address address, Integer id) {
            return addressRepository.findById(id)
                    .map(addressMap -> {
                        addressMap.setAddress(address.getAddress());
                        addressMap.setCity(address.getCity());
                        addressMap.setRegion(address.getRegion());
                        addressMap.setCountry(address.getCountry());
                        addressMap.setTypeAddress(address.getTypeAddress());
                        return addressRepository.save(addressMap);
                    })
                    .orElseThrow(() -> new AddressNotFoundException(id));
        }



}

