package io.rachidassouani.eshopbackend.category;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("categories")
public class CategoryRestController {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryRestController.class); 

	private final CategoryService categoryService;
	
	public CategoryRestController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	@PostMapping("checkCategoryUniqueness")
	public ResponseEntity<String> checkCategoryUniqueness(Integer id, String name, String alias) {		
		
		LOGGER.info("Checking category uniqueness ");
		try {
			String result = categoryService.checkCategoryUniqueness(id, name, alias);
			return new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} 
	}
}
