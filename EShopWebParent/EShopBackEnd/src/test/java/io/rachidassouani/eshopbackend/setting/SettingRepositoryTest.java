package io.rachidassouani.eshopbackend.setting;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import io.rachidassouani.eshopcommon.model.Setting;
import io.rachidassouani.eshopcommon.model.SettingCategory;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class SettingRepositoryTest {

	@Autowired
	private SettingRepository settingRepository;
	
	@Test
	public void createGeneralSettingTest() {
		//Setting setting = new Setting("SITE_NAME", "eshop", SettingCategory.GENERAL);
		
		Setting setting = new Setting("SITE_LOGO", "eshop.jpg", SettingCategory.GENERAL);
		Setting savedSetting = settingRepository.save(setting);
		
		assertThat(savedSetting).isNotNull();
		assertThat(savedSetting.getSetting_key()).isEqualTo("SITE_LOGO");
	}
	
	@Test
	public void createCurrencySettingTest() {	
		Setting currencyId = new Setting("CURRENCY_ID", "1", SettingCategory.CURRENCY);
		Setting currencySymbol = new Setting("CURRENCY_SYMBOL", "$", SettingCategory.CURRENCY);
		Setting decimalPointType = new Setting("DECIMAL_POINT_TYPE", "POINT", SettingCategory.CURRENCY);
		Setting decimalDigits = new Setting("DECIMAL_DIGITS", "2", SettingCategory.CURRENCY);
		Setting ThousandsPointType = new Setting("THOUSANDS_POINT_TYPE", "COMMA", SettingCategory.CURRENCY);
		
		settingRepository
				.saveAll(List.of(currencyId, currencySymbol, decimalPointType, decimalDigits, ThousandsPointType));
	}
	
	@Test
	public void findSettingBySettingCategoryTest() {
		SettingCategory settingCategory = SettingCategory.CURRENCY;
		
		List<Setting> settings = settingRepository.findSettingByCategory(settingCategory);
		
		assertThat(settings).isNotNull();
		assertThat(settings.size()).isGreaterThan(0);
	}
}
