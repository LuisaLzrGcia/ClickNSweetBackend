package com.clicknsweet.clicknsweet.service;

import com.clicknsweet.clicknsweet.exceptions.CardNotFoundException;
import com.clicknsweet.clicknsweet.model.Card;
import com.clicknsweet.clicknsweet.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    private final CardRepository repository;

    @Autowired
    public CardService(CardRepository repository) {
        this.repository = repository;
    }

    public List<Card> getAll() { return repository.findAll(); }

    public Card getById(Integer id) {
        return repository.findById(id).orElseThrow(() -> new CardNotFoundException(id));
    }

    public List<Card> getByUser(Integer userId) {
        return repository.findByUser_Id(userId);
    }

    public Card create(Card card) {
        return repository.save(card);
    }

    public Card update(Integer id, Card data) {
        Card existing = getById(id);
        existing.setUser(data.getUser());
        existing.setNumberCard(data.getNumberCard());
        existing.setExpirationDate(data.getExpirationDate());
        existing.setCvv(data.getCvv());
        return repository.save(existing);
    }

    public void delete(Integer id) {
        getById(id);
        repository.deleteById(id);
    }
}

