package com.alok91340.ecommerceapi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alok91340.ecommerceapi.dto.SettingDto;
import com.alok91340.ecommerceapi.entities.Setting;
import com.alok91340.ecommerceapi.repository.SettingRepository;
import com.alok91340.ecommerceapi.service.SettingService;

@RestController
@RequestMapping("api/v1/setting")
public class SettingController {
    @Autowired
    private SettingService settingService;
    @Autowired
    private SettingRepository settingRepository;

    // post first setting api
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/createSetting")
    public ResponseEntity<SettingDto> createSetting(@RequestBody SettingDto settingDto) {
        SettingDto createdSetting = settingService.addSettingFirstTime(settingDto);
        return new ResponseEntity<>(createdSetting, HttpStatus.CREATED);
    }

    // edit existing setting api
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/editSetting")
    public ResponseEntity<SettingDto> editSetting(@RequestBody SettingDto settingDto) {
        Optional<Setting> existingSetting = settingRepository.findAll().stream().findFirst();
        SettingDto editedSetting = settingService.updateSetting(settingDto, existingSetting.get().getId());

        return ResponseEntity.ok(editedSetting);
    }
}
