package com.clicknsweet.clicknsweet.repository;

import com.clicknsweet.clicknsweet.model.CommentReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentReviewRepository extends JpaRepository<CommentReview, Integer> {
    List<CommentReview> findByProduct_Id(Integer productId);
    List<CommentReview> findByUser_Id(Integer userId);
}
