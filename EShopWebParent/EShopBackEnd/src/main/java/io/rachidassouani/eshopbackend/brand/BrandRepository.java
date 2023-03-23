package io.rachidassouani.eshopbackend.brand;

import org.springframework.data.jpa.repository.JpaRepository;

import io.rachidassouani.eshopcommon.model.Brand;

public interface BrandRepository extends JpaRepository<Brand, Integer>{
	void deleteBrandByCode(String code);
	Brand findBrandByCode(String code);
	Brand findBrandByName(String name);
}