package com.alok91340.ecommerceapi.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.alok91340.ecommerceapi.dto.ContactDto;

public interface ContactService {
    ContactDto sendMessage(ContactDto contactDto, HttpServletRequest request);

    List<ContactDto> getMessages();
}
