package ${package};

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Install;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.Startup;
import org.jboss.seam.annotations.intercept.BypassInterceptors;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.security.Identity;

@Name("org.jboss.seam.security.identity")
@Scope(ScopeType.SESSION)
@Install(precedence = Install.APPLICATION)
@BypassInterceptors
@Startup
public class BelgianIdentity extends Identity {

	private static final long serialVersionUID = 1L;

	private String name;

	private String givenName;

	private String identifier;

	@Override
	protected void postAuthenticate() {
		super.postAuthenticate();
		/*
		 * Remove the "Please login first" messages.
		 */
		FacesMessages.instance().clearGlobalMessages();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getGivenName() {
		return this.givenName;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public String getIdentifier() {
		return this.identifier;
	}
}
