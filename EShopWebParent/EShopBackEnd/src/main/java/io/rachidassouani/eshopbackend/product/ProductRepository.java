package io.rachidassouani.eshopbackend.product;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import io.rachidassouani.eshopcommon.model.Product;

public interface ProductRepository extends PagingAndSortingRepository<Product, Long>{

	Product findProductByName(String name);

	@Query("UPDATE Product c SET c.enabled=:status WHERE c.code=:code")
	@Modifying
	public void enableProductStatusByCode(@Param("code") String code, @Param("status") boolean status);
}
