package io.rachidassouani.eshopcommon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.rachidassouani.eshopcommon.dto.ProductRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Table(name = "product_detail")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetail {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 255, nullable = false)
	private String name;
	
	@Column(length = 255, nullable = false)
	private String value;
	
	@ManyToOne
	@JoinColumn(name = "product_id")
	private Product product;
	
	public ProductDetail(String name, String value) {
		this.name = name;
		this.value = value;
	}
}