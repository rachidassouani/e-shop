package io.rachidassouani.eshopbackend.category;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import io.rachidassouani.eshopcommon.model.Category;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class CategoryRepositoryTest {

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Test
	public void createRootCategoryTest() {
		Category category = new Category("Desktop", "desktop", true, null);
		Category savedCategory = categoryRepository.save(category);
		
		assertThat(savedCategory).isNotNull();
		assertThat(savedCategory.getId()).isGreaterThan(0);
		assertThat(savedCategory.getName()).isEqualTo("Desktop");
	}
	
	@Test
	public void createSubCategoryTest() {
		Category parentCategory = new Category("Phones", "Phones", true, null);
		Category savedParentCategory = categoryRepository.save(parentCategory);
		
		Category subCategory = new Category("Samsung", "Samsung", true, savedParentCategory);
		Category savedSubCategory = categoryRepository.save(subCategory);
			
		assertThat(savedSubCategory).isNotNull();
		assertThat(savedSubCategory.getId()).isGreaterThan(0);
		assertThat(savedSubCategory.getParent().getName()).isEqualTo("Phones");
	}
	
	@Test
	public void getCategoryTest() {
		Category savedCategory = categoryRepository.getById(1);
			
		assertThat(savedCategory).isNotNull();
		assertThat(savedCategory.getId()).isEqualTo(1);
	}
	
	@Test
	public void getCategoryAndItsChildrenTest() {
		// 7 is phones category id
		Category savedCategory = categoryRepository.getById(7);
		
		Set<Category> children = savedCategory.getChildren();
		assertThat(children.size()).isGreaterThan(0);
	}
	
	@Test
	public void findCategoryByNameTest() {
		String categoryName = "Computers";
		Category foundedCategory = categoryRepository.findCategoryByName(categoryName);
		
		assertThat(foundedCategory).isNotNull();
		assertThat(foundedCategory.getName()).isEqualTo(categoryName);
	}
	
	@Test
	public void findCategoryByAliasTest() {
		String categoryAlias = "Phones";
		Category foundedCategory = categoryRepository.findCategoryByAlias(categoryAlias);
		
		assertThat(foundedCategory).isNotNull();
		assertThat(foundedCategory.getName()).isEqualTo(categoryAlias);
	}
	
}