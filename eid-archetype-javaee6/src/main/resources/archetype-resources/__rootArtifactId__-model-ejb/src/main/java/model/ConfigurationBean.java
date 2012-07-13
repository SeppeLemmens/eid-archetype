package ${package}.model;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import ${package}.entity.ConfigurationEntity;

@Stateless
public class ConfigurationBean {

	@PersistenceContext
	private EntityManager entityManager;

	public void setValue(ConfigurationProperty configProperty, String value) {
		ConfigurationEntity configPropertyEntity = this.entityManager.find(
				ConfigurationEntity.class, configProperty.getName());
		if (null == configPropertyEntity) {
			configPropertyEntity = new ConfigurationEntity(
					configProperty.getName(), value);
			this.entityManager.persist(configPropertyEntity);
		} else {
			configPropertyEntity.setValue(value);
		}
	}

	public String getValue(ConfigurationProperty configProperty) {
		ConfigurationEntity configPropertyEntity = this.entityManager.find(
				ConfigurationEntity.class, configProperty.getName());
		if (null == configPropertyEntity) {
			return configProperty.getDefaultValue();
		}
		String value = configPropertyEntity.getValue();
		return value;
	}
}
