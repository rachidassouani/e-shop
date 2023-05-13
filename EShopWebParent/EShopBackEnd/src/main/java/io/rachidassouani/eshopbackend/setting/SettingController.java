package io.rachidassouani.eshopbackend.setting;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import io.rachidassouani.eshopbackend.util.FileUploadService;
import io.rachidassouani.eshopcommon.model.Currency;
import io.rachidassouani.eshopcommon.model.Setting;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("settings")
@RequiredArgsConstructor
@Slf4j
public class SettingController {

	private final SettingService settingService;
	private final CurrencyRepository currencyRepository;
	

	@GetMapping
	public String findAllSettings(Model model) {
		log.info("loading list of settings");
		try {
			// loading list of settings
			List<Setting> settings = settingService.findAllSettings();
		
			for(Setting setting : settings) {
				// assigning list of settings's value to the model in order to display them in the view 'html'
				model.addAttribute(setting.getSetting_key(), setting.getValue());	
			}
			
			// loading list of currencies
			List<Currency> currencies = currencyRepository.findAllByOrderByNameAsc();
			// assigning list of currencies to the model in order to display them in the view 'html'
			model.addAttribute("currencies", currencies);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "settings/settings";
	}	
	
	@PostMapping("saveGeneral")
	public String saveGeneralSettings(
			@RequestParam("fileImage") MultipartFile multipartFile,
			HttpServletRequest request,
			RedirectAttributes redirectAttributes) throws IOException {

		log.info("saving general settings");
		
		GeneralSettingBag settingBag = settingService.findGeneralSettingBag();
		
		saveSiteLogo(multipartFile, settingBag);
		saveCurrencySymbol(request, settingBag);		
		updateSettingValues(request, settingBag.findAllSettings());
		
		redirectAttributes.addFlashAttribute("successMessage", "General settings saved successfully");

		return "redirect:/settings";
	}

	private void saveSiteLogo(MultipartFile multipartFile, GeneralSettingBag settingBag) throws IOException {
		if (!multipartFile.isEmpty()) {
			String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

			String value = "/siteLogo/" + fileName;
			
			settingBag.updateSiteLogo(value);
			
			FileUploadService.saveFile("../siteLogo", fileName, multipartFile);
		}
	}
	
	private void saveCurrencySymbol(HttpServletRequest request, GeneralSettingBag generalSettingBag) {
		
		Integer currencyId = Integer.parseInt(request.getParameter("CURRENCY_ID"));
		
		Optional<Currency> foundedCurrency = currencyRepository.findById(currencyId);
		
		if (foundedCurrency.isPresent()) {
			Currency currency =  foundedCurrency.get();
			generalSettingBag.updateCurrencySymbol(currency.getSymbol());
		}
	}
	
	private void updateSettingValues(HttpServletRequest request, List<Setting> settings) {
		for (Setting setting : settings) {
			String value = request.getParameter(setting.getSetting_key());
			if (value != null) {
				setting.setValue(value);
			}
		}
		settingService.saveAllSettings(settings);
	}
}