package ${package}.admin;

import java.io.Serializable;

import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.jboss.seam.security.Authenticator;
import org.jboss.seam.security.BaseAuthenticator;
import org.jboss.seam.security.Identity;
import org.picketlink.idm.impl.api.model.SimpleUser;

public class AdminAuthenticator extends BaseAuthenticator implements
		Authenticator, Serializable {

	private static final long serialVersionUID = 1L;

	// @Inject
	private Logger log = Logger.getLogger(AdminAuthenticator.class);

	@Inject
	private Identity identity;

	@Override
	public void authenticate() {
		this.log.debug("authenticate");

		// TODO: check this in the database
		this.identity.addRole("admin", "USERS", "GROUP");
		
		setStatus(AuthenticationStatus.SUCCESS);
		setUser(new SimpleUser("TODO"));
	}
}
