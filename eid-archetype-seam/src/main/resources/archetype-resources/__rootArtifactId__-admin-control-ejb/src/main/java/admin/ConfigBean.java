package ${package}.admin;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;

import org.jboss.ejb3.annotation.LocalBinding;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.security.Admin;
import org.jboss.seam.log.Log;

import ${package}.model.Configuration;
import ${package}.model.ConfigurationProperty;

@Stateful
@Name("${admin-control-seam-components-prefix}Config")
@LocalBinding(jndiBinding = "${admin-control-jndi-pattern-prefix}ConfigBean")
public class ConfigBean implements Config {

	@Logger
	private Log log;

	private String idpUrl;

	private String idpFingerprint;

	private String idpRolloverFingerprint;

	@EJB
	private Configuration configuration;

	@Override
	@PostConstruct
	public void postConstruct() {
		this.log.debug("postConstruct");
		this.idpUrl = this.configuration
				.getValue(ConfigurationProperty.EID_IDP_URL);
		this.idpFingerprint = this.configuration
				.getValue(ConfigurationProperty.EID_IDP_FINGERPRINT);
		this.idpRolloverFingerprint = this.configuration
				.getValue(ConfigurationProperty.EID_IDP_ROLLOVER_FINGERPRINT);
	}

	@Override
	public String getIdpUrl() {
		return this.idpUrl;
	}

	@Override
	public void setIdpUrl(String idpUrl) {
		this.idpUrl = idpUrl;
	}

	@Override
	public String getIdpFingerprint() {
		return this.idpFingerprint;
	}

	@Override
	public void setIdpFingerprint(String idpFingerprint) {
		this.idpFingerprint = idpFingerprint;
	}

	@Override
	public String getIdpRolloverFingerprint() {
		return idpRolloverFingerprint;
	}

	@Override
	public void setIdpRolloverFingerprint(String idpRolloverFingerprint) {
		this.idpRolloverFingerprint = idpRolloverFingerprint;
	}

	@Override
	@Admin
	public void save() {
		this.log.debug("save");
		this.configuration.setValue(ConfigurationProperty.EID_IDP_URL,
				this.idpUrl);
		this.configuration.setValue(ConfigurationProperty.EID_IDP_FINGERPRINT,
				this.idpFingerprint);
		this.configuration.setValue(
				ConfigurationProperty.EID_IDP_ROLLOVER_FINGERPRINT,
				this.idpRolloverFingerprint);
	}

	@Remove
	@Destroy
	@Override
	public void destroy() {
		this.log.debug("destroy");
	}
}
