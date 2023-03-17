package com.alok91340.ecommerceapi.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.alok91340.ecommerceapi.dto.CommentDto;
import com.alok91340.ecommerceapi.entities.User;

public interface CommentService {
    CommentDto createComment(User customer, Long productId, CommentDto commentDto);

    List<CommentDto> findCommentByUser(User customer);

    List<CommentDto> getAllComments();

    List<CommentDto> getAllCommentsByProductId(Long productId);

    CommentDto getCommentById(Long productId, Long commentId);

    CommentDto updateComment(Long productId, CommentDto commentDto, Long commentId);

    void deleteComment(Long productId, Long commentId);
}
