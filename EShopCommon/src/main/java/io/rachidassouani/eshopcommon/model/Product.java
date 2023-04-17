package io.rachidassouani.eshopcommon.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 20, nullable = false, unique = true)
	private String code;
	
	@Column(length = 256, nullable = false, unique = true)
	private String name;
	
	@Column(length = 256, nullable = false, unique = true)
	private String alias;
	
	@Column(name="short_description", length = 512, nullable = false)
	private String shortDescription;
	
	@Column(name="full_description", length = 4096, nullable = false)
	private String fullDescription;
	
	@Column(name = "created_time")
	private LocalDateTime createdTime;
	
	@Column(name = "updated_time")
	private LocalDateTime updatedTime;
	
	private boolean enabled;
	
	@Column(name = "in_stock")
	private boolean inStock;
	
	private double cost;
	private double price;
	
	@Column(name = "discount_percent")
	private double discountPercent;
	
	private double length;
	private double width;
	private double height;
	private double weight;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name="brand_id")
	private Brand brand;
	
	@Override
	public String toString() {
		return String.format("Product [id=%s, code=%s, name=%s, alias=%s, enabled=%s, inStock=%s, cost=%s, price=%s]",
				id, code, name, alias, enabled, inStock, cost, price);
	}	
}