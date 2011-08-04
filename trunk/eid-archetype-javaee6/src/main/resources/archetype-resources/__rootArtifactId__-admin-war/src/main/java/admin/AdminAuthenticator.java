package ${package}.admin;

import java.io.Serializable;
import java.security.cert.X509Certificate;

import javax.inject.Inject;
import javax.servlet.http.HttpSession;

import org.jboss.logging.Logger;
import org.jboss.seam.security.Authenticator;
import org.jboss.seam.security.BaseAuthenticator;
import org.jboss.seam.security.Identity;
import org.picketlink.idm.impl.api.model.SimpleUser;

import ${package}.model.AdministratorManager;

public class AdminAuthenticator extends BaseAuthenticator implements
		Authenticator, Serializable {

	private static final long serialVersionUID = 1L;

	// @Inject
	private Logger log = Logger.getLogger(AdminAuthenticator.class);

	@Inject
	private Identity identity;

	@Inject
	private HttpSession httpSession;

	@Inject
	private AdministratorManager administratorManager;

	@Override
	public void authenticate() {
		X509Certificate authenticatedCertificate = (X509Certificate) this.httpSession
				.getAttribute("eid.certs.authn");
		be.fedict.eid.applet.service.Identity eIDIdentity = (be.fedict.eid.applet.service.Identity) this.httpSession
				.getAttribute("eid.identity");
		this.log.debug("authenticate: "
				+ authenticatedCertificate.getSubjectX500Principal());

		if (this.administratorManager.hasAdminRights(authenticatedCertificate,
				eIDIdentity)) {
			this.identity.addRole("admin", "USERS", "GROUP");
		}

		setStatus(AuthenticationStatus.SUCCESS);
		setUser(new SimpleUser(eIDIdentity.getNationalNumber()));
	}
}