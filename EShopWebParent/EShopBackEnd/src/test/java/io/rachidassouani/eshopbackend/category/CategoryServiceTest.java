package io.rachidassouani.eshopbackend.category;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.rachidassouani.eshopbackend.util.Constant;
import io.rachidassouani.eshopcommon.model.Category;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class CategoryServiceTest {
	
	@MockBean
	private CategoryRepository categoryRepository;
	
	@InjectMocks
	private CategoryServiceImpl categoryService;
	
	@Test
	public void testCheckUniqueCategoryInNewModeAndReturnDuplicateName() {
		Integer id = null;
		String name = "Computerss";
		String alias = "test";
		
		Category category = new Category(id, name, alias);
		
		Mockito.when(categoryRepository.findCategoryByName(name)).thenReturn(category);
		Mockito.when(categoryRepository.findCategoryByAlias(alias)).thenReturn(null);
		
		String result = categoryService.checkCategoryUniqueness(id, name, alias);
		
		assertThat(result).isEqualTo(Constant.CATEGORY_NAME_IS_DUPLICATED);
	}
	
	@Test
	public void testCheckUniqueCategoryInNewModeAndReturnDuplicateAlias() {
		Integer id = null;
		String name = "Computerss";
		String alias = "test";
		
		Category category = new Category(id, name, alias);
		
		Mockito.when(categoryRepository.findCategoryByName(name)).thenReturn(null);
		Mockito.when(categoryRepository.findCategoryByAlias(alias)).thenReturn(category);
		
		String result = categoryService.checkCategoryUniqueness(id, name, alias);
		
		assertThat(result).isEqualTo(Constant.CATEGORY_ALIAS_IS_DUPLICATED);
	}
	
	@Test
	public void testCheckUniqueCategoryInNewModeAndReturnOK() {
		Integer id = null;
		String name = "Computerss";
		String alias = "test";
		
		Mockito.when(categoryRepository.findCategoryByName(name)).thenReturn(null);
		Mockito.when(categoryRepository.findCategoryByAlias(alias)).thenReturn(null);
		
		String result = categoryService.checkCategoryUniqueness(id, name, alias);
		
		assertThat(result).isEqualTo(Constant.OK);
	}

	@Test
	public void testCheckUniqueCategoryInEditModeAndReturnDuplicateName() {
		Integer id = 1;
		String name = "Computerss";
		String alias = "test";
		
		Category category = new Category(2, name, alias);
		
		Mockito.when(categoryRepository.findCategoryByName(name)).thenReturn(category);
		Mockito.when(categoryRepository.findCategoryByAlias(alias)).thenReturn(null);
		
		String result = categoryService.checkCategoryUniqueness(id, name, alias);
		
		assertThat(result).isEqualTo(Constant.CATEGORY_NAME_IS_DUPLICATED);
	}
	
	@Test
	public void testCheckUniqueCategoryInEditModeAndReturnDuplicateAlias() {
		Integer id = 1;
		String name = "Computerss";
		String alias = "test";
		
		Category category = new Category(2, name, alias);
		
		Mockito.when(categoryRepository.findCategoryByName(name)).thenReturn(null);
		Mockito.when(categoryRepository.findCategoryByAlias(alias)).thenReturn(category);
		
		String result = categoryService.checkCategoryUniqueness(id, name, alias);
		
		assertThat(result).isEqualTo(Constant.CATEGORY_ALIAS_IS_DUPLICATED);
	}
	
	@Test
	public void testCheckUniqueCategoryInEditModeAndReturnOK() {
		Integer id = 1;
		String name = "Computerss";
		String alias = "test";
		
		Category category = new Category(id, name, alias);
		Mockito.when(categoryRepository.findCategoryByName(name)).thenReturn(category);
		Mockito.when(categoryRepository.findCategoryByAlias(alias)).thenReturn(category);
		
		String result = categoryService.checkCategoryUniqueness(1, name, alias);
		
		assertThat(result).isEqualTo(Constant.OK);
	}
}