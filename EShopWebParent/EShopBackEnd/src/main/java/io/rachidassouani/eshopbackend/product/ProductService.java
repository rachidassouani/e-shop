package io.rachidassouani.eshopbackend.product;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.rachidassouani.eshopcommon.model.Product;

@Service
@Transactional
public class ProductService {

	private final ProductRepository productRepository;	
	
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public List<Product> findAllProducts() {
		return (List<Product>) productRepository.findAll();
	}
}
