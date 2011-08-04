package ${package}.admin;

import java.security.cert.X509Certificate;

import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import be.fedict.eid.applet.service.Identity;

public class BelgianIdentityCardProducer {

	@Inject
	private HttpSession httpSession;

	@Produces
	public Identity createIdentity() {
		Identity identity = (Identity) this.httpSession
				.getAttribute("eid.identity");
		return identity;
	}

	@Produces
	@AuthCert
	public X509Certificate createAuthCert() {
		X509Certificate authCert = (X509Certificate) this.httpSession
				.getAttribute("eid.certs.authn");
		return authCert;
	}
}
