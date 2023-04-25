package io.rachidassouani.eshopcommon.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

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
	
	@Column(name = "main_image_name", nullable = false)
	private String mainImageName;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private Set<ProductImage> productImage; 
	
	public void addExtraImage(String imageName) {
		this.productImage.add(new ProductImage(imageName, this));
	}
	
	@Transient
	public String getMainImagePath() {
		if (this.code == null || this.mainImageName == null)
			return "/images/image-thumbnail.png";
		return "/productImages/" + this.code + "/" + this.mainImageName;
	}

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<ProductDetail> productDetails;
	
	@Override
	public String toString() {
		return String.format("Product [id=%s, code=%s, name=%s, alias=%s, enabled=%s, inStock=%s, cost=%s, price=%s]",
				id, code, name, alias, enabled, inStock, cost, price);
	}	
}