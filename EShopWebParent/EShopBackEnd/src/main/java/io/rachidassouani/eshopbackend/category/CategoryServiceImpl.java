package io.rachidassouani.eshopbackend.category;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.rachidassouani.eshopbackend.util.Constant;
import io.rachidassouani.eshopcommon.model.Category;

@Transactional
@Service
public class CategoryServiceImpl implements CategoryService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryServiceImpl.class); 
	
	@Autowired
	private CategoryRepository categoryRepository;

	@Override
	public Category saveCategory(Category category) {
		LOGGER.info("saving category");
		return categoryRepository.save(category);
	}

	@Override
	public List<Category> findAllCategories() {
		LOGGER.info("finding all categories");
		List<Category> categoris = categoryRepository.findAll();
		return categoris;
	}
	
	@Override
	public List<Category> findAllCategoriesForForm() {
		LOGGER.info("finding all categories to be displayed in the form");
		List<Category> categoris = categoryRepository.findAll();
		return categoris;
	}

	@Override
	public Category findCategoryByCode(String code) throws CategoryNotFoundException {
		Category category = categoryRepository.findCategoryByCode(code);
		if (category == null)
			throw new CategoryNotFoundException(Constant.CATEGORY_NOT_FOUND);
		return category;
	}

	@Override
	public void deleteCategoryByCode(String code) throws CategoryNotFoundException {
		
		Category category = categoryRepository.findCategoryByCode(code);
		if (category == null)
			throw new CategoryNotFoundException(
					String.format(Constant.CAN_NOT_DELETE_CATEGORY, code));
		
		categoryRepository.delete(category);
	}
}