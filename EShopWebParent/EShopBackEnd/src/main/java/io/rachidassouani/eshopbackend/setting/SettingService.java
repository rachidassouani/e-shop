package io.rachidassouani.eshopbackend.setting;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.rachidassouani.eshopcommon.model.Setting;
import io.rachidassouani.eshopcommon.model.SettingCategory;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class SettingService {

	private final SettingRepository settingRepository;

	
	public List<Setting> findAllSettings() throws Exception {
		return settingRepository.findAll();
	}
	
	public GeneralSettingBag findGeneralSettingBag() {
		
		List<Setting> settings = new ArrayList<>();
		
		List<Setting> generalSettings = settingRepository
				.findSettingByCategory(SettingCategory.GENERAL);
		
		List<Setting> currencySettings = settingRepository
				.findSettingByCategory(SettingCategory.CURRENCY);
		
		settings.addAll(generalSettings);
		settings.addAll(currencySettings);
		
		return new GeneralSettingBag(settings);
	}

	public void saveAllSettings(List<Setting> settings) {
		settingRepository.saveAll(settings);
	}	
}
