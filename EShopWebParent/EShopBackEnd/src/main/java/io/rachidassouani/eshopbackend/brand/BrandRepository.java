package io.rachidassouani.eshopbackend.brand;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import io.rachidassouani.eshopcommon.model.Brand;

public interface BrandRepository extends JpaRepository<Brand, Integer>{
	void deleteBrandByCode(String code);
	Brand findBrandByCode(String code);
	Brand findBrandByName(String name);
	
	@Query("SELECT NEW Brand(b.id, b.code, b.name) FROM Brand b ORDER BY b.name ASC")
	public List<Brand> findAllBrands();
}