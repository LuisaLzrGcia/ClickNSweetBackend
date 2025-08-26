package com.clicknsweet.clicknsweet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "comments_reviews")
public class CommentReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_review_id")
    private Integer id;


    @ManyToOne(optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    @JsonIgnore
    private Product product;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "rating", nullable = false)
    private Integer rating; //

    @Column(name = "comment_detail")
    private String commentDetail;

    @Column(name = "comment_date")
    private LocalDateTime commentDate;

    public CommentReview() {
    }

    public CommentReview(Integer id, Product product, User user, Integer rating, String commentDetail, LocalDateTime commentDate) {
        this.id = id;
        this.product = product;
        this.user = user;
        this.rating = rating;
        this.commentDetail = commentDetail;
        this.commentDate = commentDate;
    }

    // Getters y Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Integer getRating() { return rating; }
    public void setRating(Integer rating) { this.rating = rating; }

    public String getCommentDetail() { return commentDetail; }
    public void setCommentDetail(String commentDetail) { this.commentDetail = commentDetail; }

    public LocalDateTime getCommentDate() { return commentDate; }
    public void setCommentDate(LocalDateTime commentDate) { this.commentDate = commentDate; }

    @Override
    public String toString() {
        return "CommentReview{" +
                "id=" + id +
                ", product=" + (product != null ? product.getId() : null) +
                ", user=" + (user != null ? user.getId() : null) +
                ", rating=" + rating +
                ", commentDetail='" + commentDetail + '\'' +
                ", commentDate=" + commentDate +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CommentReview c)) return false;
        return Objects.equals(id, c.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}

