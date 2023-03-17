package com.alok91340.ecommerceapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.alok91340.ecommerceapi.entities.Comment;
import com.alok91340.ecommerceapi.entities.User;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByProductId(Long productId);

    List<Comment> findByUser(User customer);
}
