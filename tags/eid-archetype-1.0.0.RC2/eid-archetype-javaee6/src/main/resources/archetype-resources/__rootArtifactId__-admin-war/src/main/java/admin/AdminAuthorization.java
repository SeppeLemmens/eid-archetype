package ${package}.admin;

import org.jboss.seam.security.Identity;
import org.jboss.seam.security.annotations.Secures;

public class AdminAuthorization {

	@Secures
	@Admin
	public boolean isAdmin(Identity identity) {
		return identity.hasRole("admin", "USERS", "GROUP");
	}
}
