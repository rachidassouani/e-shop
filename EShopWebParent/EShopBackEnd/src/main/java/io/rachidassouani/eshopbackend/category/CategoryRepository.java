package io.rachidassouani.eshopbackend.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.rachidassouani.eshopcommon.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
	
	public Category findCategoryByCode(String code);
	public Category findCategoryByName(String name);
	public Category findCategoryByAlias(String alias);
}
