package ${package}.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = Constants.DATABASE_TABLE_PREFIX + "config")
public class ConfigurationEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;

	private String value;

	public ConfigurationEntity() {
		super();
	}

	public ConfigurationEntity(String name, String value) {
		this.name = name;
		this.value = value;
	}

	@Id
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(length = 10 * 1024)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
