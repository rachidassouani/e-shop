package io.rachidassouani.eshopcommon.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SettingBag {

	private List<Setting> settings;	
	
	public Setting getSettingByKey(String key) {
		int index = settings.indexOf(new Setting(key));
		if (index >= 0) {
			return settings.get(index);
		}
		return null;
	}
	
	public String getValue(String key) {
		Setting setting = getSettingByKey(key);
		if (setting == null) {
			return null;
		}
		return setting.getValue();
	}
	
	public void updateSetting(String key, String value) {
		Setting setting = getSettingByKey(key);
		if (setting == null || value == null) {
			return;
		}
		setting.setValue(value);
	}
	
	public List<Setting> findAllSettings() {
		return settings;
	}
}
