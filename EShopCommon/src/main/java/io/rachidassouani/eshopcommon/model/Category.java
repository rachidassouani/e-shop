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


@Entity
@Table(name = "category")
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

	// Constructors	
	public Category() {}

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

	/*
	 * Getters & Setters
	 */
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public Set<Category> getChildren() {
		return children;
	}

	public void setChildren(Set<Category> children) {
		this.children = children;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Transient
	public String getImagePath() {
		if (this.id == null )
			return "/images/image-thumbnail.png";
		
		return "/categoriesImage" + this.code + this.imageName; 
	}
	
	// toString method
	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", alias=" + alias + ", imageName=" + imageName + ", enabled="
				+ enabled + "]";
	}	
}
