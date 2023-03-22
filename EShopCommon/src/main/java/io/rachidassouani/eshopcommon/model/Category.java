package io.rachidassouani.eshopcommon.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Entity
@Table(name = "category")
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Category {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(length = 20, nullable = false, unique = true)
	private String code;
	
	@Column(length = 128, nullable = false, unique = true)
	private String name;
	
	@Column(length = 64, nullable = false, unique = true)
	private String alias;
	
	@Column(length = 128, nullable = false)
	private String imageName;
	
	private boolean enabled;
	
	@OneToOne
	@JoinColumn(name = "parent_id")
	private Category parent;
	
	@OneToMany(mappedBy = "parent")	
	private Set<Category> children = new HashSet<>();


	/*
	 * Constructors
	 */

	public Category(String name, String alias, boolean enabled, Category parent) {
		super();
		this.name = name;
		this.alias = alias;
		this.enabled = enabled;
		this.parent = parent;
		this.imageName = "default.png";
	}
	
	public Category(Integer id, String name, String alias) {
		this.id = id;
		this.name = name;
		this.alias = alias;
	}

	
	@Transient
	public String getImagePath() {
		if (this.id == null )
			return "/images/image-thumbnail.png";
		
		return "/categoriesImage" + this.code + this.imageName; 
	}	
}