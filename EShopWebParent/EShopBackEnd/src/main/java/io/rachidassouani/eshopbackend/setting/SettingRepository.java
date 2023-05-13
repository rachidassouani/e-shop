package io.rachidassouani.eshopbackend.setting;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.rachidassouani.eshopcommon.model.Setting;
import io.rachidassouani.eshopcommon.model.SettingCategory;

public interface SettingRepository extends JpaRepository<Setting, String> {

	public List<Setting> findSettingByCategory(SettingCategory settingCategory);
}
