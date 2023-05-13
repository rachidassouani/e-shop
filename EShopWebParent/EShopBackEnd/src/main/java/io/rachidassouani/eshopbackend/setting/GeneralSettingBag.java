package io.rachidassouani.eshopbackend.setting;

import java.util.List;

import io.rachidassouani.eshopcommon.model.Setting;
import io.rachidassouani.eshopcommon.model.SettingBag;

public class GeneralSettingBag extends SettingBag {

	public GeneralSettingBag(List<Setting> settings) {
		super(settings);
	}

	public void updateCurrencySymbol(String value) {
		super.updateSetting("CURRENCY_SYMBOL", value);
	}
	
	public void updateSiteLogo(String value) {
		super.updateSetting("SITE_LOGO", value);
	}
}
