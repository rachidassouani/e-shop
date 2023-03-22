package io.rachidassouani.eshopbackend.brand;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.rachidassouani.eshopbackend.util.Constant;
import io.rachidassouani.eshopcommon.model.Brand;

@Service
@Transactional
public class BrandService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BrandService.class); 
	
	private final BrandRepository brandRepository;
	
	public BrandService(BrandRepository brandRepository) {
		this.brandRepository = brandRepository;
	}
	
	
	List<Brand> findAllBrands() {
		LOGGER.info("finding all brands");
		return brandRepository.findAll();
	}


	public Page<Brand> findBrandsPerPage(int pageNumber) {
		LOGGER.info("finding brands per page number, current page equals " + pageNumber);		
		Pageable pageable = PageRequest.of(pageNumber - 1, Constant.BRANDS_PER_PAGE);
		return brandRepository.findAll(pageable);
	}
}