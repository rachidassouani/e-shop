package io.rachidassouani.eshopcommon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Setting {
	@Id
	@Column(name="setting_key", nullable = false, length = 128)
	private String setting_key;
	
	@Column(nullable = false, length = 1024)
	private String value;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 45)
	private SettingCategory category;
	
	public Setting(String key) {
		this.setting_key = key;
	}
}
