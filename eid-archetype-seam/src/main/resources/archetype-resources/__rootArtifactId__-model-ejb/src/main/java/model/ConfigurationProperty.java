package ${package}.model;

public enum ConfigurationProperty {

	EID_IDP_URL("eid-idp-url",
			"https://www.e-contract.be/eid-idp/protocol/saml2/post/auth-ident"), EID_IDP_FINGERPRINT(
			"eid-idp-fingerprint"), EID_IDP_ROLLOVER_FINGERPRINT(
			"eid-idp-rollover-fingerprint"), EID_IDP_ISSUER_NAME(
					"eid-idp-issuer-name", "${application-title}");

	private final String name;

	private final String defaultValue;

	private ConfigurationProperty(String name, String defaultValue) {
		this.name = name;
		this.defaultValue = defaultValue;
	}

	private ConfigurationProperty(String name) {
		this(name, null);
	}

	public String getName() {
		return this.name;
	}

	public String getDefaultValue() {
		return this.defaultValue;
	}
}
