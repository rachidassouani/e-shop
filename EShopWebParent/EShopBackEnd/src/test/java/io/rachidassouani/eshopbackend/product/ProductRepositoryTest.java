package io.rachidassouani.eshopbackend.product;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import io.rachidassouani.eshopbackend.util.RandomCodeService;
import io.rachidassouani.eshopcommon.model.Brand;
import io.rachidassouani.eshopcommon.model.Category;
import io.rachidassouani.eshopcommon.model.Product;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProductRepositoryTest {

	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private TestEntityManager entityManager;
	
	@Test
	public void createProductTest() {
		Brand brand = entityManager.find(Brand.class, 1);
		Category category = entityManager.find(Category.class, 4);
		
		Product product = Product.builder()
				.code(RandomCodeService.generatCode())
				.name("Samsung Galaxy A31")
				.alias("Samsung_Galaxy_A31")
				.shortDescription("Best samsung Galaxy A31")
				.fullDescription("this is the best Samsung Galaxy A31 ever")
				.enabled(true)
				.inStock(true)
				.cost(5000)
				.price(108700)
				.brand(brand)
				.category(category)
				.createdTime(LocalDateTime.now())
				.updatedTime(LocalDateTime.now())				
				.build();
		
		Product savedProduct = productRepository.save(product);
		
		assertThat(savedProduct).isNotNull();
		assertThat(savedProduct.getId()).isGreaterThan(0);
	}
	
	@Test
	public void findAllProductsTest() {
		productRepository.findAll().forEach(System.out::println);
	}
	
	@Test
	public void findProductByIdTest() {
		Product product = productRepository.findById(1L).get();
		
		assertThat(product).isNotNull();
		assertThat(product.getId()).isEqualTo(1);
	}
	
	@Test
	public void updateProductTest() {
		Product product = productRepository.findById(2L).get();

		product.setName("Iphone 14 Pro");
		product.setAlias("Iphone_14_Pro");
		Product savedProduct = productRepository.save(product);
		
		assertThat(savedProduct).isNotNull();
		assertThat(savedProduct.getName()).isEqualTo("Iphone 14 Pro");
	}
	
	@Test
	public void deleteProductTest() {
		productRepository.deleteById(1L);
		Optional<Product> foundedProduct = productRepository.findById(2L);
		
		assertThat(!foundedProduct.isPresent());
	}
	
	@Test
	public void saveProductWithImagesTest() {
		Product product = productRepository.findById(5L).get();
		
		product.setMainImageName("main-image.jpg");
		
		product.addExtraImage("image-1.jpg");
		product.addExtraImage("image-2.jpg");
		product.addExtraImage("image-3.jpg");
		product.addExtraImage("image-4.jpg");
				
		productRepository.save(product);
	}
	
}
