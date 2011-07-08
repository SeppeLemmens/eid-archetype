package ${package};

import java.security.cert.X509Certificate;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jboss.ejb3.annotation.LocalBinding;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;

import ${package}.AdministratorManager;

@Name("${admin-control-seam-components-prefix}Authenticator")
@Stateless
@LocalBinding(jndiBinding = "${admin-control-jndi-pattern-prefix}AuthenticatorBean")
public class AuthenticatorBean implements Authenticator {

	@In
	Credentials credentials;

	@In
	Identity identity;

	@In(value = "eid.certs.authn", scope = ScopeType.SESSION)
	private X509Certificate authenticatedCertificate;

	@EJB
	private AdministratorManager administratorManager;

	public boolean authenticate() {
		if (this.administratorManager
				.hasAdminRights(this.authenticatedCertificate)) {
			this.identity.addRole("admin");
		}
		return true;
	}
}