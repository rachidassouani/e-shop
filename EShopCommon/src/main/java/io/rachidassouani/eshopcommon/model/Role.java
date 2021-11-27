package io.rachidassouani.eshopcommon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 40, nullable = false, unique = true)
	private String name;

	@Column(length = 200)
	private String description;

	/*
	 * Constructors
	 */

	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Role(String name, String description) {
		super();
		this.name = name;
		this.description = description;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
