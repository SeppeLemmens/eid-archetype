package ${package}.admin;

import javax.ejb.Local;

@Local
public interface Config {

	/*
	 * Accessors.
	 */
	String getIdpUrl();

	void setIdpUrl(String idpUrl);

	String getIdpFingerprint();

	void setIdpFingerprint(String idpFingerprint);

	String getIdpRolloverFingerprint();

	void setIdpRolloverFingerprint(String idpRolloverFingerprint);

	/*
	 * Actions.
	 */
	void save();

	/*
	 * Lifecycle.
	 */
	void destroy();

	void postConstruct();
}
