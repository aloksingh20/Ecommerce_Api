package com.alok91340.ecommerceapi.service.Impl;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.alok91340.ecommerceapi.Exception.EcommerceApiException;
import com.alok91340.ecommerceapi.Exception.ResourceNotFoundException;
import com.alok91340.ecommerceapi.dto.CommentDto;
import com.alok91340.ecommerceapi.entities.Comment;
import com.alok91340.ecommerceapi.entities.Product;
import com.alok91340.ecommerceapi.entities.User;
import com.alok91340.ecommerceapi.repository.CommentRepository;
import com.alok91340.ecommerceapi.repository.ProductRepository;
import com.alok91340.ecommerceapi.repository.UserRepository;
import com.alok91340.ecommerceapi.service.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

    private static Logger logger = LoggerFactory.getLogger(CommentServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDto createComment(User customer, Long productId, CommentDto commentDto) {
        User user = this.userRepository.findById(customer.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found", customer.getId()));

        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found", productId));

        Comment comment = mapToEntity(commentDto);
        comment.setUser(user);
        comment.setProduct(product);
        Comment savedComment = this.commentRepository.save(comment);

        return mapToDto(savedComment);
    }

    @Override
    public List<CommentDto> findCommentByUser(User customer) {
        List<Comment> comments = commentRepository.findByUser(customer);
        if (comments.size() == 0) {
            throw new EcommerceApiException("User has no comment or review", HttpStatus.BAD_REQUEST);
        }
        List<CommentDto> commentDtoList = comments.stream()
                .map(comment -> mapToDto(comment))
                .collect(Collectors.toList());
        return commentDtoList;
    }

    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentDto> commentDtoList = comments.stream()
                .map(comment -> mapToDto(comment))
                .collect(Collectors.toList());
        return commentDtoList;
    }

    @Override
    public List<CommentDto> getAllCommentsByProductId(Long productId) {
        List<Comment> comments = commentRepository.findByProductId(productId);
        List<CommentDto> commentDtoList = comments.stream()
                .map(comment -> mapToDto(comment))
                .collect(Collectors.toList());
        return commentDtoList;
    }

    @Override
    public CommentDto getCommentById(Long productId, Long commentId) {

        Product product = this.productRepository.findById(productId).orElseThrow();
        Comment comment = this.commentRepository.findById(commentId).orElseThrow();

        if (!comment.getProduct().getId().equals(product.getId())) {
            throw new EcommerceApiException("Comment does not belong to product", HttpStatus.BAD_REQUEST);
        }

        return mapToDto(comment);
    }

    @Override
    public CommentDto updateComment(Long productId, CommentDto commentDto, Long commentId) {
        Product product = this.productRepository.findById(productId).orElseThrow();
        Comment comment = this.commentRepository.findById(commentId).orElseThrow();

        if (!comment.getProduct().getId().equals(product.getId())) {
            throw new EcommerceApiException("Comment does not belong to product", HttpStatus.BAD_REQUEST);
        }
        comment.setRate(commentDto.getRate());
        comment.setRate(commentDto.getRate());
        Comment updatedComment = commentRepository.save(comment);

        return mapToDto(updatedComment);
    }

    @Override
    public void deleteComment(Long productId, Long commentId) {
        Product product = this.productRepository.findById(productId).orElseThrow();
        Comment comment = this.commentRepository.findById(commentId).orElseThrow();

        if (!comment.getProduct().getId().equals(product.getId())) {
            throw new EcommerceApiException("Comment does not belong to product", HttpStatus.BAD_REQUEST);
        }
        commentRepository.delete(comment);
    }

    private Comment mapToEntity(CommentDto commentDto) {
        return this.modelMapper.map(commentDto, Comment.class);
    }

    // image uploading
    private String uploadCommentImage(MultipartFile file) {
        CommentDto commentDto = new CommentDto();
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        if (fileName.contains("..")) {
            logger.error("It is a valid file");
        }
        try {
            commentDto.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return commentDto.getImage();
    }

    // map to dto
    private CommentDto mapToDto(Comment comment) {
        return this.modelMapper.map(comment, CommentDto.class);
    }

}
