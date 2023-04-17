package io.rachidassouani.eshopbackend.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("products")
@Slf4j
public class ProductRestController {

	private final ProductService productService;
	
	public ProductRestController(ProductService productService) {
		this.productService = productService;
	}
	
	@PostMapping("checkProductUniqueness")
	public ResponseEntity<String> checkProductUniqueness(Long id, String name) {		
		
		log.info("Checking product uniqueness");
		try {
			String result = productService.checkProductUniqueness(id, name);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}
}
