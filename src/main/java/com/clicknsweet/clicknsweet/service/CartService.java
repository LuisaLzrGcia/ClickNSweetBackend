package com.clicknsweet.clicknsweet.service;

import com.clicknsweet.clicknsweet.exceptions.CartNotFoundException;
import com.clicknsweet.clicknsweet.model.Cart;
import com.clicknsweet.clicknsweet.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {

    private final CartRepository repository;

    @Autowired
    public CartService(CartRepository repository) {
        this.repository = repository;
    }

    public List<Cart> getAll() { return repository.findAll(); }

    public Cart getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new CartNotFoundException(id));
    }

    public Cart getByUser(Long userId) {
        return repository.findByUser_Id(userId).orElseThrow(() -> new CartNotFoundException("para el usuario " + userId));
    }

    public boolean existsForUser(Long userId) {
        return repository.existsByUser_Id(userId);
    }

    public Cart create(Cart cart) {
        return repository.save(cart);
    }

    public Cart update(Long id, Cart data) {
        Cart existing = getById(id);
        existing.setUser(data.getUser()); // si cambias el user, respeta UNIQUE en BD
        return repository.save(existing);
    }

    public void delete(Long id) {
        getById(id);
        repository.deleteById(id);
    }
}

