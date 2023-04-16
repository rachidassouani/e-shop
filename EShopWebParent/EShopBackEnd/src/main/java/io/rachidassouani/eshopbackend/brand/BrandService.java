package io.rachidassouani.eshopbackend.brand;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.rachidassouani.eshopbackend.category.CategoryService;
import io.rachidassouani.eshopbackend.util.Constant;
import io.rachidassouani.eshopcommon.model.Brand;
import io.rachidassouani.eshopcommon.model.Category;

@Service
@Transactional
public class BrandService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BrandService.class); 
	
	private final BrandRepository brandRepository;
	
	private final CategoryService categoryService;
	
	public BrandService(BrandRepository brandRepository, CategoryService categoryService) {
		this.brandRepository = brandRepository;
		this.categoryService = categoryService;
	}
	
	public List<Brand> findAllBrands() {
		LOGGER.info("finding all brands");
		return brandRepository.findAllBrands();
	}

	public Page<Brand> findBrandsPerPage(int pageNumber) {
		LOGGER.info("finding brands per page number, current page equals " + pageNumber);		
		Pageable pageable = PageRequest.of(pageNumber - 1, Constant.BRANDS_PER_PAGE);
		return brandRepository.findAll(pageable);
	}

	public List<Category> findAllCategoriesInBrandsForm() {
		return categoryService.findAllCategoriesForForm();
	}

	public void deleteCategoryByCode(String code) {
		LOGGER.info("Deleting brandby code" + code);		
		brandRepository.deleteBrandByCode(code);	
	}

	public Brand saveBrand(Brand brand) {
		LOGGER.info("saving brand");
		return brandRepository.save(brand);
	}

	public Brand findBrandByCode(String code) throws BrandNotFoundException {
		LOGGER.info("Finding brand by code = " + code);
		return brandRepository.findBrandByCode(code);
	}
	
	public Brand findBrandById(Integer id) throws BrandNotFoundException {
		LOGGER.info("Finding brand by id = " + id);
		return brandRepository.findById(id).get();
	}

	public String checkBrandUniqueness(Integer id, String name) {
		
		// check if name and alias are valid
		if (name == null || name.isBlank())
			return Constant.INVALID_CATEGORY_NAME;
		
		Brand brandByName = brandRepository.findBrandByName(name);
		
		// check for creating new brand
		if (id == null || id == 0) {
			if (brandByName != null) {
				return Constant.BRAND_NAME_IS_DUPLICATED;
			}
		
		// check for edit existing brand
		} else {
			if (brandByName != null && !brandByName.getId().equals(id)) {
				return Constant.BRAND_NAME_IS_DUPLICATED;	
			}
		}
		return Constant.OK;
	}
}