package io.rachidassouani.eshopbackend.product;

import org.springframework.data.repository.PagingAndSortingRepository;

import io.rachidassouani.eshopcommon.model.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long>{

}
