package io.rachidassouani.eshopbackend.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import io.rachidassouani.eshopcommon.model.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer>{
	
	public Category findCategoryByCode(String code);
	public Category findCategoryByName(String name);
	public Category findCategoryByAlias(String alias);
	
	@Query("UPDATE Category c SET c.enabled=:status WHERE c.code=:code")
	@Modifying
	public void enableCategoryStatusByCode(@Param("code") String code, @Param("status") boolean status);
}
