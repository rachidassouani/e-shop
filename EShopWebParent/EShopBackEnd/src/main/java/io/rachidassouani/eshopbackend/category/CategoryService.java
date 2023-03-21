package io.rachidassouani.eshopbackend.category;

import java.util.List;

import org.springframework.data.domain.Page;

import io.rachidassouani.eshopcommon.model.Category;

public interface CategoryService {

	Category saveCategory(Category category);
	List<Category> findAllCategories();
	List<Category> findAllCategoriesForForm();
	Category findCategoryByCode(String code) throws CategoryNotFoundException;
	void deleteCategoryByCode(String code) throws CategoryNotFoundException;
	String checkCategoryUniqueness(Integer id, String name, String alias);
	void enableCategoryStatusByCode(String code, boolean status);
	Page<Category> findAllCategoriesPerPageNumber(int pageNumber); 
}
