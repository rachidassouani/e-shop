package io.rachidassouani.eshopbackend.category;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

	@Override
	public String checkCategoryUniqueness(Integer id, String name, String alias) {
		
		// check if name and alias are valid
		if (name == null || name.isBlank())
			return Constant.INVALID_CATEGORY_NAME;
		
		if (alias == null || alias.isBlank())
			return Constant.INVALID_CATEGORY_ALIAS;
		
		Category categoryByName = categoryRepository.findCategoryByName(name);
		Category categoryByAlias = categoryRepository.findCategoryByAlias(alias);
		
		// check for creating new category
		if (id == null || id == 0) {
			if (categoryByName != null) {
				return Constant.CATEGORY_NAME_IS_DUPLICATED;
			
			} else if (categoryByAlias != null){
				return Constant.CATEGORY_ALIAS_IS_DUPLICATED;	
			}
		
		// check for edit existing category
		} else {
			if (categoryByName != null && !categoryByName.getId().equals(id)) {
				return Constant.CATEGORY_NAME_IS_DUPLICATED;	
			}
			else if (categoryByAlias != null && !categoryByAlias.getId().equals(id)) {
				return Constant.CATEGORY_ALIAS_IS_DUPLICATED;	
			}
		}
		return Constant.OK;
	}

	@Override
	public void enableCategoryStatusByCode(String code, boolean status) {
		categoryRepository.enableCategoryStatusByCode(code, status);
	}

	@Override
	public Page<Category> findAllCategoriesPerPageNumber(int pageNumber) {
		Pageable pageable = PageRequest.of(pageNumber - 1, Constant.CATEGORIES_PER_PAGE);
		return categoryRepository.findAll(pageable);		
	}
}