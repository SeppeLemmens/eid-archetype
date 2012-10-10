package ${package}.admin;

import java.security.cert.X509Certificate;
import java.util.List;

import be.fedict.eid.applet.service.spi.AuthenticationService;
import be.fedict.eid.applet.service.spi.CertificateSecurityException;
import be.fedict.eid.applet.service.spi.ExpiredCertificateSecurityException;
import be.fedict.eid.applet.service.spi.RevokedCertificateSecurityException;
import be.fedict.eid.applet.service.spi.TrustCertificateSecurityException;

public class AdminAuthenticationService implements AuthenticationService {

	@Override
	public void validateCertificateChain(List<X509Certificate> certificateChain)
			throws ExpiredCertificateSecurityException,
			RevokedCertificateSecurityException,
			TrustCertificateSecurityException, CertificateSecurityException,
			SecurityException {
		/*
		 * Admin trust is based on the public key only.
		 */
	}
}
