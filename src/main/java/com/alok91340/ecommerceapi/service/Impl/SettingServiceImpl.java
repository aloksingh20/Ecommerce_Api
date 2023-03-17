package com.alok91340.ecommerceapi.service.Impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.alok91340.ecommerceapi.Exception.EcommerceApiException;
import com.alok91340.ecommerceapi.Exception.ResourceNotFoundException;
import com.alok91340.ecommerceapi.dto.SettingDto;
import com.alok91340.ecommerceapi.entities.Setting;
import com.alok91340.ecommerceapi.repository.SettingRepository;
import com.alok91340.ecommerceapi.service.SettingService;

@Service
public class SettingServiceImpl implements SettingService {

    @Autowired
    private SettingRepository settingRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SettingDto addSettingFirstTime(SettingDto settingDto) {
        Optional<Setting> setting = settingRepository.findAll().stream().findFirst();
        if (!setting.isPresent()) {
            Setting firstSetting = mapToEntity(settingDto);
            Setting saveSetting = settingRepository.save(firstSetting);
            return mapToDto(saveSetting);
        } else {
            throw new EcommerceApiException("Setting already exists, please edit it only", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public SettingDto updateSetting(SettingDto settingDto, Long id) {
        Setting setting = this.settingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("setting not found", id));
        setting.setAboutUs(settingDto.getAboutUs());
        setting.setAddress(settingDto.getAddress());
        setting.setCompany(settingDto.getCompany());
        setting.setContact(settingDto.getContact());
        setting.setDescription(settingDto.getDescription());
        setting.setEmail(settingDto.getEmail());
        setting.setFacebook(settingDto.getFacebook());
        setting.setFax(settingDto.getFax());
        setting.setInstagram(settingDto.getInstagram());
        setting.setKeywords(settingDto.getKeywords());
        setting.setPhone(settingDto.getPhone());
        setting.setTwitter(settingDto.getTwitter());
        setting.setSmtpEmail(settingDto.getSmtpEmail());
        setting.setSmtpPassword(settingDto.getSmtpPassword());
        setting.setSmtpPort(settingDto.getSmtpPort());
        setting.setSmtpServer(settingDto.getSmtpServer());
        Setting savedSetting = this.settingRepository.save(setting);
        return mapToDto(savedSetting);
    }

    private Setting mapToEntity(SettingDto settingDto) {
        return this.modelMapper.map(settingDto, Setting.class);
    }

    private SettingDto mapToDto(Setting setting) {
        return this.modelMapper.map(setting, SettingDto.class);
    }

}
