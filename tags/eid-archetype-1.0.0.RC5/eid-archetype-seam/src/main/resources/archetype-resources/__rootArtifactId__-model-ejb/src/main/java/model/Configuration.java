package ${package}.model;

import javax.ejb.Local;

@Local
public interface Configuration {

	void setValue(ConfigurationProperty configProperty, String value);

	String getValue(ConfigurationProperty configProperty);
}
