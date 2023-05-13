package io.rachidassouani.eshopbackend.product;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.rachidassouani.eshopbackend.brand.BrandNotFoundException;
import io.rachidassouani.eshopbackend.brand.BrandService;
import io.rachidassouani.eshopbackend.category.CategoryNotFoundException;
import io.rachidassouani.eshopbackend.category.CategoryService;
import io.rachidassouani.eshopbackend.util.Constant;
import io.rachidassouani.eshopbackend.util.RandomCodeService;
import io.rachidassouani.eshopcommon.dto.ProductRequest;
import io.rachidassouani.eshopcommon.model.Brand;
import io.rachidassouani.eshopcommon.model.Category;
import io.rachidassouani.eshopcommon.model.Product;
import io.rachidassouani.eshopcommon.model.ProductDetail;

@Service
@Transactional
public class ProductService {

	private final ProductRepository productRepository;
	private final BrandService brandService;
	private final CategoryService categoryService;

	public ProductService(ProductRepository productRepository, BrandService brandService,
			CategoryService categoryService) {

		this.productRepository = productRepository;
		this.brandService = brandService;
		this.categoryService = categoryService;
	}

	public List<Product> findAllProducts() {
		return (List<Product>) productRepository.findAll();
	}

	public Product save(ProductRequest productRequest) throws CategoryNotFoundException, BrandNotFoundException {

		// finding brand by brand code
		Brand brand = brandService.findBrandByCode(productRequest.getBrandCode());

		// finding category by brand code
		Category category = categoryService.findCategoryByCode(productRequest.getCategoryCode());

		/*
		 * If the alias that comes with the request is empty, the alias in this case
		 * will be the product name. all spaces that alias has will be replaced by
		 * dashes (-)
		 */
		String alias = "";
		if (productRequest.getAlias() != null && !productRequest.getAlias().isEmpty()) {
			alias = productRequest.getAlias().trim().replaceAll(" ", "-");
		} else {
			alias = productRequest.getName().trim().replaceAll(" ", "-");
		}
		
		Product productToSave = Product.builder()
				.code(RandomCodeService.generatCode())
				.name(productRequest.getName())
				.alias(alias).brand(brand)
				.category(category)
				.enabled(productRequest.isEnabled())
				.inStock(productRequest.isInStock())
				.shortDescription(productRequest.getShortDescription())
				.fullDescription(productRequest
				.getFullDescription())
				.cost(productRequest.getCost())
				.price(productRequest.getPrice())
				.discountPercent(productRequest.getDiscountPercent())
				.mainImageName(productRequest.getMainImageName())
				.length(productRequest.getLength())
				.width(productRequest.getWidth())
				.height(productRequest.getHeight())
				.weight(productRequest.getWeight())
				
				
				.createdTime(LocalDateTime.now())
				.updatedTime(LocalDateTime.now())
				.build();

		// assign product detail to product model 
		for (ProductDetail pd : productRequest.getProductDetails()) {
			pd.setProduct(productToSave);
		}
		productToSave.setProductDetails(productRequest.getProductDetails());
		
		
		Product savedProduct = productRepository.save(productToSave);
		return savedProduct;
	}

	public String checkProductUniqueness(Long id, String name) {

		// check if name and alias are valid
		if (name == null || name.isBlank())
			return Constant.INVALID_CATEGORY_NAME;

		Product productByName = productRepository.findProductByName(name);

		// check for creating new product
		if (id == null || id == 0) {
			if (productByName != null) {
				return Constant.PRODUCT_NAME_IS_DUPLICATED;
			}

		// check for edit existing product
		} else {
			if (productByName != null && !productByName.getId().equals(id)) {
				return Constant.PRODUCT_NAME_IS_DUPLICATED;
			}
		}
		return Constant.OK;
	}

	public void enableProductStatusByCode(String code, boolean status) {
		productRepository.enableProductStatusByCode(code, status);
	}

	public void deleteProductByCode(String code) throws ProductNotFoundException {
		Product product = productRepository.findProductByCode(code);
		if (product == null)
			throw new ProductNotFoundException(
					String.format(Constant.CAN_NOT_DELETE_PRODUCT, code));
		
		productRepository.delete(product);
	}

	public Product findProductByCode(String code) throws ProductNotFoundException {	
		Product product = productRepository.findProductByCode(code);
		if (product == null)
			throw new ProductNotFoundException(Constant.PRODUCT_NOT_FOUND);
		return product;
	}
}