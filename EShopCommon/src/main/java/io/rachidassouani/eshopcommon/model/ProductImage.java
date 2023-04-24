package io.rachidassouani.eshopcommon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
public class ProductImage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	@Setter 
	private Long id;
	
	@Column(nullable = false)
	@Getter
	@Setter
	private String name;
	
	@ManyToOne
	@JoinColumn(name="product_id")
	private Product product;
	
	public ProductImage(String imageName, Product product) {
		this.name = imageName;
		this.product = product;
	}
}