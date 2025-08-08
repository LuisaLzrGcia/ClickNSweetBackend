package com.clicknsweet.clicknsweet.service;

import com.clicknsweet.clicknsweet.exceptions.CommentReviewNotFoundException;
import com.clicknsweet.clicknsweet.model.CommentReview;
import com.clicknsweet.clicknsweet.repository.CommentReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentReviewService {

    private final CommentReviewRepository repository;

    @Autowired
    public CommentReviewService(CommentReviewRepository repository) {
        this.repository = repository;
    }

    public List<CommentReview> getAll() {
        return repository.findAll();
    }

    public CommentReview getById(Integer id) {
        return repository.findById(id)
                .orElseThrow(() -> new CommentReviewNotFoundException(id));
    }

    public List<CommentReview> getByProduct(Integer productId) {
        return repository.findByProduct_Id(productId);
    }

    public List<CommentReview> getByUser(Integer userId) {
        return repository.findByUser_Id(userId);
    }

    public CommentReview create(CommentReview cr) {
        return repository.save(cr);
    }

    public CommentReview update(Integer id, CommentReview data) {
        CommentReview existing = getById(id);
        // Campos editables
        existing.setRating(data.getRating());
        existing.setCommentDetail(data.getCommentDetail());
        existing.setProduct(data.getProduct());
        existing.setUser(data.getUser());
        // Comment_date lo maneja la BD; si quieres permitir editar, descomenta:
        // existing.setCommentDate(data.getCommentDate());
        return repository.save(existing);
    }

    public void delete(Integer id) {
        // dispara not found si no existe
        getById(id);
        repository.deleteById(id);
    }
}

