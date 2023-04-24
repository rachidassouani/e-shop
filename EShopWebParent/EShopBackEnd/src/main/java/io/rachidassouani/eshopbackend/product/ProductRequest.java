package io.rachidassouani.eshopbackend.product;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class ProductRequest {
	private Long id;
	private String code;
	private String name;
	private String alias;
	private String shortDescription;
	private String fullDescription;
	private LocalDateTime createdTime;
	private LocalDateTime updatedTime;
	private boolean enabled;
	private boolean inStock;
	private double cost;
	private double price;
	private double discountPercent;
	private double length;
	private double width;
	private double height;
	private double weight;
	private String categoryCode;
	private String brandCode;
	private String mainImageName;
	
	public String getMainImagePath() {
		if (this.code == null || this.mainImageName == null)
			return "/images/image-thumbnail.png";
		return "/productImages/" + this.code + "/" + this.mainImageName;
	}
}