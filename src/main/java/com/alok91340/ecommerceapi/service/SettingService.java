package com.alok91340.ecommerceapi.service;

import com.alok91340.ecommerceapi.dto.SettingDto;

public interface SettingService {
    SettingDto addSettingFirstTime(SettingDto settingDto);

    SettingDto updateSetting(SettingDto settingDto, Long id);
}
