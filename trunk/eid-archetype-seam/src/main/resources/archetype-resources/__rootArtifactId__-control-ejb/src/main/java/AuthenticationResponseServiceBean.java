package ${package};

import java.security.PrivateKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.crypto.SecretKey;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.ejb3.annotation.LocalBinding;

import be.fedict.eid.idp.common.SamlAuthenticationPolicy;
import be.fedict.eid.idp.sp.protocol.saml2.spi.AuthenticationResponseService;
import ${package}.model.ConfigurationProperty;
import ${package}.model.Configuration;

@Stateless
@Local(AuthenticationResponseService.class)
@LocalBinding(jndiBinding = "${control-jndi-pattern-prefix}AuthenticationResponseServiceBean")
public class AuthenticationResponseServiceBean implements
		AuthenticationResponseService {

	private static final Log LOG = LogFactory
			.getLog(AuthenticationResponseServiceBean.class);

	@EJB
	private Configuration configuration;

	@Override
	public void validateServiceCertificate(
			SamlAuthenticationPolicy authenticationPolicy,
			List<X509Certificate> certificateChain) throws SecurityException {
		LOG.debug("validateServiceCertificate");
		String idpFingerprint = this.configuration
				.getValue(ConfigurationProperty.EID_IDP_FINGERPRINT);
		if (null == idpFingerprint || idpFingerprint.isEmpty()) {
			LOG.warn("No eID IdP fingerprint configured. Cannot check SAML signature.");
			return;
		}
		X509Certificate idpCertificate = certificateChain.get(0);
		String actualFingerprint;
		try {
			actualFingerprint = DigestUtils.shaHex(idpCertificate.getEncoded());
		} catch (CertificateEncodingException e) {
			throw new SecurityException("certificate encoding error: "
					+ e.getMessage(), e);
		}
		if (false == idpFingerprint.equals(actualFingerprint)) {
			LOG.debug("IdP fingerprint mismatch");
			String idpRolloverFingerprint = this.configuration
					.getValue(ConfigurationProperty.EID_IDP_ROLLOVER_FINGERPRINT);
			if (null == idpRolloverFingerprint
					|| idpRolloverFingerprint.isEmpty()) {
				throw new SecurityException("IdP fingerprint mismatch");
			}
			LOG.debug("trying rollover IdP fingerprint");
			if (false == idpRolloverFingerprint.equals(actualFingerprint)) {
				throw new SecurityException("IdP fingerprint mismatch");
			}
		}

		LOG.debug("IdP fingerprint matches");
	}

	@Override
	public int getMaximumTimeOffset() {
		return 10;
	}

	@Override
	public PrivateKey getAttributePrivateKey() {
		return null;
	}

	@Override
	public SecretKey getAttributeSecretKey() {
		return null;
	}

	@Override
	public boolean requiresResponseSignature() {
		String idpFingerprint = this.configuration.getValue(
				ConfigurationProperty.EID_IDP_FINGERPRINT);
		if (null == idpFingerprint || idpFingerprint.isEmpty()) {
			LOG.warn("no IdP fingerprint configured");
			return false;
		}
		return true;
	}
}
