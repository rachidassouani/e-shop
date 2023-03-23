package io.rachidassouani.eshopbackend.brand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
