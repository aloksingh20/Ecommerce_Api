package com.alok91340.ecommerceapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.alok91340.ecommerceapi.dto.CommentDto;
import com.alok91340.ecommerceapi.entities.User;
import com.alok91340.ecommerceapi.service.CommentService;
import com.alok91340.ecommerceapi.service.CommonService;
import com.alok91340.ecommerceapi.utils.isAuthenticatedAsAdminOrUser;

@RestController
@RequestMapping("api/v1/products")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommonService commonService;

    @isAuthenticatedAsAdminOrUser
    @PostMapping("/{productId}/createComment")
    public ResponseEntity<CommentDto> createComment(@AuthenticationPrincipal final Authentication authentication,
            @PathVariable final Long productId,
            @RequestPart("productDto") final CommentDto commentDto) {
        final User customer = commonService.getCurrentAuthenticatedUser(authentication);
        final CommentDto responseComment = commentService.createComment(customer, productId, commentDto);
        return new ResponseEntity<>(responseComment, HttpStatus.CREATED);
    }

    // get comment by user
    @isAuthenticatedAsAdminOrUser
    @GetMapping("/comment/findByUser")
    public List<CommentDto> findByUser(@AuthenticationPrincipal final Authentication authentication) {
        final User customer = commonService.getCurrentAuthenticatedUser(authentication);
        return commentService.findCommentByUser(customer);
    }

    // get all comments api
    @GetMapping("/getAllComments")
    public List<CommentDto> getAllComments() {
        return commentService.getAllComments();
    }

    // get comments by product id api
    @GetMapping("/{productId}/getCommentByProductId")
    public List<CommentDto> getCommentByProductId(@PathVariable final Long productId) {
        return commentService.getAllCommentsByProductId(productId);
    }

    // get comment by id api
    @GetMapping("/{productId}/comment/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable final Long productId,
            @PathVariable final Long commentId) {
        final CommentDto responseComment = commentService.getCommentById(productId, commentId);
        return ResponseEntity.ok(responseComment);
    }

    // update comment api
    @PutMapping("/{productId}/update/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable final Long productId,
            @RequestBody final CommentDto commentDto,
            @PathVariable final Long commentId) {
        final CommentDto responseComment = commentService.updateComment(productId, commentDto, commentId);
        return ResponseEntity.ok(responseComment);
    }

    // delete comment api
    @DeleteMapping("/{productId}/delete/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable final Long productId,
            @PathVariable final Long commentId) {
        commentService.deleteComment(productId, commentId);
        return ResponseEntity.ok("Comment with id: " + commentId + " is successfully:)");
    }

}
