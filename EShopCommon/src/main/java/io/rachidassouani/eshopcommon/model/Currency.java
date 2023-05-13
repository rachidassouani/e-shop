package io.rachidassouani.eshopcommon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Currency {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(nullable = false, length = 64)
	private String name;
	
	@Column(nullable = false, length = 3)
	private String symbol;
	
	@Column(nullable = false, length = 4)
	private String code;	
	
	public Currency(String name, String symbol, String code) {
		this.name = name;
		this.symbol = symbol;
		this.code = code;	
	}
	
}
