package ${package}.admin;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import ${package}.model.Configuration;
import ${package}.model.ConfigurationProperty;

@Named(Constants.CDI_PREFIX + "ConfigController")
@RequestScoped
public class ConfigurationController {

	private String idpUrl;

	private String idpIssuerName;

	private String idpFingerprint;

	private String idpRolloverFingerprint;

	@EJB
	private Configuration configuration;
	
	@PostConstruct
	public void postConstruct() {
		this.idpUrl = this.configuration
				.getValue(ConfigurationProperty.EID_IDP_URL);
		this.idpFingerprint = this.configuration
				.getValue(ConfigurationProperty.EID_IDP_FINGERPRINT);
		this.idpRolloverFingerprint = this.configuration
				.getValue(ConfigurationProperty.EID_IDP_ROLLOVER_FINGERPRINT);
		this.idpIssuerName = this.configuration
				.getValue(ConfigurationProperty.EID_IDP_ISSUER_NAME);
	}

	public String save() {
		this.configuration.setValue(ConfigurationProperty.EID_IDP_URL,
				this.idpUrl);
		this.configuration.setValue(ConfigurationProperty.EID_IDP_FINGERPRINT,
				this.idpFingerprint);
		this.configuration.setValue(
				ConfigurationProperty.EID_IDP_ROLLOVER_FINGERPRINT,
				this.idpRolloverFingerprint);
		this.configuration.setValue(ConfigurationProperty.EID_IDP_ISSUER_NAME,
				this.idpIssuerName);
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_INFO, "Configuration successfully saved.", null));
		return "/admin/configuration";
	}

	public String getIdpUrl() {
		return this.idpUrl;
	}

	public void setIdpUrl(String idpUrl) {
		this.idpUrl = idpUrl;
	}

	public String getIdpIssuerName() {
		return this.idpIssuerName;
	}

	public void setIdpIssuerName(String idpIssuerName) {
		this.idpIssuerName = idpIssuerName;
	}

	public String getIdpFingerprint() {
		return this.idpFingerprint;
	}

	public void setIdpFingerprint(String idpFingerprint) {
		this.idpFingerprint = idpFingerprint;
	}

	public String getIdpRolloverFingerprint() {
		return this.idpRolloverFingerprint;
	}

	public void setIdpRolloverFingerprint(String idpRolloverFingerprint) {
		this.idpRolloverFingerprint = idpRolloverFingerprint;
	}
}
