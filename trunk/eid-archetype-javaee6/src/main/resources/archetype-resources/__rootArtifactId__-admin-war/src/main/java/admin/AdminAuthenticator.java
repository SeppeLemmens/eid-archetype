package ${package}.admin;

import java.io.Serializable;
import java.security.cert.X509Certificate;

import javax.inject.Inject;

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
	private AdministratorManager administratorManager;

	@Inject
	private be.fedict.eid.applet.service.Identity eIDIdentity;

	@Inject
	@AuthCert
	X509Certificate authCert;

	@Override
	public void authenticate() {
		this.log.debug("authenticate: "
				+ this.authCert.getSubjectX500Principal());

		if (this.administratorManager
				.hasAdminRights(this.authCert, eIDIdentity)) {
			this.identity.addRole("admin", "USERS", "GROUP");
		}

		setStatus(AuthenticationStatus.SUCCESS);
		setUser(new SimpleUser(this.eIDIdentity.getNationalNumber()));
	}
}