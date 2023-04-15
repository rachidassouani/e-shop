package io.rachidassouani.eshopcommon.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Brand {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false, unique = true, length = 20)
	private String code;
	
	@Column(nullable = false, unique = true, length = 50)
	private String name;
	
	@Column(nullable = false, length = 128)
	private String logo;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "brand_category", joinColumns = @JoinColumn(name = "brand_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
	private Set<Category> categories = new HashSet<>();
	
	public Brand(int id, String code, String name) {
		this.id = id;
		this.code = code;
		this.name = name;
	}
	
	@Transient
	public String getLogoPath() {
		if (this.id == null)
			return "/images/image-thumbnail.png";
		
		return "/brandsLogo/" + this.code + "/" + this.logo;
	}

	@Override
	public String toString() {
		return String.format("Brand [id=%s, code=%s, name=%s]", id, code, name);
	}
}