package com.clicknsweet.clicknsweet.service;

import com.clicknsweet.clicknsweet.exceptions.CartItemNotFoundException;
import com.clicknsweet.clicknsweet.model.CartItem;
import com.clicknsweet.clicknsweet.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartItemService {

    private final CartItemRepository repository;

    @Autowired
    public CartItemService(CartItemRepository repository) {
        this.repository = repository;
    }

    public List<CartItem> getAll() { return repository.findAll(); }

    public CartItem getById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new CartItemNotFoundException(id));
    }

    public List<CartItem> getByCart(Integer cartId) {
        return repository.findByCart_Id(cartId);
    }

    public CartItem create(CartItem item) {
        // Si ya existe (Cart_ID, Product_ID), podrías sumar cantidad:
        return repository.findByCart_IdAndProduct_Id(item.getCart().getId(), item.getProduct().getId())
                .map(existing -> {
                    existing.setQuantity(existing.getQuantity() + item.getQuantity());
                    return repository.save(existing);
                })
                .orElseGet(() -> repository.save(item));
    }

    public CartItem upsertByCartAndProduct(Long cartId, Long productId, Integer quantity) {
        return repository.findByCart_IdAndProduct_Id(cartId, productId)
                .map(existing -> {
                    existing.setQuantity(quantity);
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("No existe el item para actualizar (cartId=" + cartId + ", productId=" + productId + ")"));
    }

    public CartItem update(Integer id, CartItem data) {
        CartItem existing = getById(id);
        existing.setCart(data.getCart());
        existing.setProduct(data.getProduct());
        existing.setQuantity(data.getQuantity());
        return repository.save(existing);
    }

    public void delete(Integer id) {
        getById(id);
        repository.deleteById(id);
    }
}

