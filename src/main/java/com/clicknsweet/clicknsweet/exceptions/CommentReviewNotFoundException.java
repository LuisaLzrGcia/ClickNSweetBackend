package com.clicknsweet.clicknsweet.exceptions;

public class CommentReviewNotFoundException extends RuntimeException {
  public CommentReviewNotFoundException(Integer id) {
    super("No se encuentra el comentario/reseña " + id);
  }
}

