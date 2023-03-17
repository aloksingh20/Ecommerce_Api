package com.alok91340.ecommerceapi.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alok91340.ecommerceapi.dto.ContactDto;
import com.alok91340.ecommerceapi.entities.Contact;
import com.alok91340.ecommerceapi.repository.ContactRepository;
import com.alok91340.ecommerceapi.service.ContactService;
import com.alok91340.ecommerceapi.utils.RequestClientIp;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ContactDto sendMessage(ContactDto contactDto, HttpServletRequest request) {
        Contact contact = mapToEntity(contactDto);
        String ipAddress = RequestClientIp.getClientIpAddress(request);
        contact.setIpAddress(ipAddress);
        Contact savedContact = this.contactRepository.save(contact);
        return mapToDto(savedContact);

    }

    @Override
    public List<ContactDto> getMessages() {
        List<Contact> messages = contactRepository.findAll();

        return messages.stream()
                .map(message -> mapToDto(message))
                .collect(Collectors.toList());
    }

    private Contact mapToEntity(ContactDto contactDto) {
        return this.modelMapper.map(contactDto, Contact.class);
    }

    private ContactDto mapToDto(Contact contact) {
        return this.modelMapper.map(contact, ContactDto.class);
    }

}
