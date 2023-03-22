package io.rachidassouani.eshopbackend.brand;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import io.rachidassouani.eshopbackend.category.CategoryRepository;
import io.rachidassouani.eshopcommon.model.Brand;
import io.rachidassouani.eshopcommon.model.Category;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class BrandRepositoryTest {
	
	@Autowired
	private BrandRepository brandRepository;

	@Autowired
	private CategoryRepository categoryRepository;
	
	@Test
	public void createBrandTest() {
		
		Category laptops = categoryRepository.findCategoryByName("Laptops"); 
		
		Brand acer = new Brand();
		acer.setName("Acer");
		acer.setLogo("default-logo.png");

		acer.getCategories().add(laptops);
		
		Brand savedBrand = brandRepository.save(acer);
		
		assertThat(savedBrand).isNotNull();
		assertThat(savedBrand.getId()).isGreaterThan(0);
	}
	
	@Test
	public void createBrandContainTwoCategoriesTest() {
		
		Category tablets = categoryRepository.findCategoryByName("Tablets"); 
		Category telephones = categoryRepository.findCategoryByName("Telephones"); 
		
		Brand apple = new Brand();
		apple.setName("apple");
		apple.setLogo("default-logo.png");

		apple.getCategories().add(tablets);
		apple.getCategories().add(telephones);
		
		Brand savedBrand = brandRepository.save(apple);
		
		assertThat(savedBrand).isNotNull();
		assertThat(savedBrand.getId()).isGreaterThan(0);
		assertThat(savedBrand.getCategories().size()).isEqualTo(2);
	}

	@Test
	public void findAllBrandsTest() {
		List<Brand> allBrands = brandRepository.findAll();

		assertThat(allBrands).isNotNull();
		assertThat(allBrands.size()).isGreaterThan(0);
	}
	
	@Test
	public void findBrandByIdTest() {
		Brand brand = brandRepository.findById(1).get();

		assertThat(brand).isNotNull();
		assertThat(brand.getName()).isEqualTo("Acer");
	}
	
	@Test
	public void updateBrandNamedTest() {
		Brand brand = brandRepository.findById(1).get();

		brand.setName("Acer");
		brandRepository.save(brand);
		
		assertThat(brand).isNotNull();
		assertThat(brand.getName()).isEqualTo("Acer");
	}
	
	@Test
	public void deleteBrandTest() {
		Integer id = 2;
		brandRepository.deleteById(id);
		
		Optional<Brand> deletedBrand = brandRepository.findById(id);
		
		assertThat(deletedBrand.isEmpty());
	}
}