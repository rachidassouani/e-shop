package io.rachidassouani.eshopbackend.brand;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.rachidassouani.eshopcommon.model.Brand;
import io.rachidassouani.eshopcommon.model.Category;

@RestController
@RequestMapping("brands")
public class BrandRestController {
	private static final Logger LOGGER = LoggerFactory.getLogger(BrandRestController.class); 

	private final BrandService brandService;
	
	public BrandRestController(BrandService brandService) {
		this.brandService = brandService;
	}
	
	@PostMapping("checkBrandUniqueness")
	public ResponseEntity<String> checkBrandUniqueness(Integer id, String name) {		
		
		LOGGER.info("Checking brand uniqueness ");
		try {
			String result = brandService.checkBrandUniqueness(id, name);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}
	
	
	@GetMapping("{brandCode}/categories")
	public ResponseEntity<?> findAllCategoriesByBrandCode(@PathVariable("brandCode") String brandCode) throws BrandNotFoundRestException{		
		
		LOGGER.info("finding list of categories by brand's code");
		try {
			Brand brand = brandService.findBrandByCode(brandCode);
			Set<Category> categories = brand.getCategories();
			
			List<CategoryDTO> categoryDTOs = new ArrayList<>();
			
			for(Category category : categories) {
				CategoryDTO categoryDTO = new CategoryDTO(category.getId(), category.getCode(), category.getName());
				categoryDTOs.add(categoryDTO);
			}
			return new ResponseEntity<>(categoryDTOs, HttpStatus.OK);
		} catch (BrandNotFoundException e) {
			throw new BrandNotFoundRestException();
		} 
	} 
	
	
}
