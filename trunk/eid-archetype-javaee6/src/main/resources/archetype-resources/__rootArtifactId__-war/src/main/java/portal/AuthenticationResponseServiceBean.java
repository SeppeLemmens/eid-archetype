package ${package}.portal;

import java.security.PrivateKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.crypto.SecretKey;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.commons.codec.digest.DigestUtils;

import ${package}.model.ConfigurationBean;
import ${package}.model.ConfigurationProperty;
import be.fedict.eid.idp.common.SamlAuthenticationPolicy;
import be.fedict.eid.idp.sp.protocol.saml2.spi.AuthenticationResponseService;

@Stateless
@Local(AuthenticationResponseService.class)
public class AuthenticationResponseServiceBean implements
		AuthenticationResponseService {

	@EJB
	private ConfigurationBean configurationBean;

	@Override
	public boolean requiresResponseSignature() {
		return true;
	}

	@Override
	public void validateServiceCertificate(
			SamlAuthenticationPolicy authenticationPolicy,
			List<X509Certificate> certificateChain) throws SecurityException {
		if (SamlAuthenticationPolicy.AUTHENTICATION_WITH_IDENTIFICATION != authenticationPolicy) {
			throw new SecurityException("invalid authentication policy");
		}
		String fingerprint = this.configurationBean
				.getValue(ConfigurationProperty.EID_IDP_FINGERPRINT);
		String rolloverFingerprint = this.configurationBean
				.getValue(ConfigurationProperty.EID_IDP_ROLLOVER_FINGERPRINT);
		X509Certificate idpCertificate = certificateChain.get(0);
		String actualFingerprint;
		try {
			actualFingerprint = DigestUtils.shaHex(idpCertificate.getEncoded());
		} catch (CertificateEncodingException e) {
			throw new SecurityException("certificate encoding error");
		}
		if (actualFingerprint.equals(fingerprint)) {
			return;
		}
		if (actualFingerprint.equals(rolloverFingerprint)) {
			return;
		}
		throw new SecurityException("certificate fingerprint mismatch");
	}

	@Override
	public int getMaximumTimeOffset() {
		return 5;
	}

	@Override
	public SecretKey getAttributeSecretKey() {
		return null;
	}

	@Override
	public PrivateKey getAttributePrivateKey() {
		return null;
	}
}
