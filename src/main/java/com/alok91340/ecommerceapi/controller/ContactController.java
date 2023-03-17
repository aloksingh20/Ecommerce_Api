package com.alok91340.ecommerceapi.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alok91340.ecommerceapi.dto.ContactDto;
import com.alok91340.ecommerceapi.service.ContactService;

@RestController
@RequestMapping("api/v1/contact")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping("/sendMessage")
    public ResponseEntity<ContactDto> sendMessage(@RequestBody ContactDto contactDto,
            HttpServletRequest request) {
        ContactDto responseMessage = contactService.sendMessage(contactDto, request);
        return new ResponseEntity<>(responseMessage, HttpStatus.CREATED);
    }

    // get messages api
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/allMessages")
    public List<ContactDto> getAllMessages() {
        return contactService.getMessages();
    }
}