package io.rachidassouani.eshopcommon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "role")
@Data
@NoArgsConstructor
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

	public Role(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}


	/*
	 * toString Method
	 */
	
	@Override
	public String toString() {
		//return "Role [id=" + id + ", name=" + name + ", description=" + description + "]";
		return name;
	}	
}