package ${package}.admin;

import java.io.Serializable;
import java.security.cert.X509Certificate;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.logging.Logger;
import org.jboss.seam.security.Authenticator;
import org.jboss.seam.security.BaseAuthenticator;
import org.jboss.seam.security.Identity;
import org.picketlink.idm.impl.api.model.SimpleUser;

import be.fedict.eid.applet.service.cdi.BelgianCertificate;
import be.fedict.eid.applet.service.cdi.BelgianCertificate.CERTIFICATE_TYPE;

import ${package}.model.AdministratorManager;

@Named(Constants.CDI_PREFIX + "Authenticator")
public class AdminAuthenticator extends BaseAuthenticator implements
		Authenticator, Serializable {

	private static final long serialVersionUID = 1L;

	// @Inject
	private Logger log = Logger.getLogger(AdminAuthenticator.class);

	@Inject
	private Identity identity;

	@EJB
	private AdministratorManager administratorManager;

	@Inject
	private be.fedict.eid.applet.service.Identity eIDIdentity;

	@Inject
	@BelgianCertificate(CERTIFICATE_TYPE.AUTH)
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
		String name = this.eIDIdentity.getFirstName() + " " + this.eIDIdentity.getName();
		setUser(new SimpleUser(name));
	}
}