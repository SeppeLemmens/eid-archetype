package ${package}.admin;

import org.jboss.seam.faces.rewrite.FacesRedirect;
import org.jboss.seam.faces.security.AccessDeniedView;
import org.jboss.seam.faces.security.LoginView;
import org.jboss.seam.faces.view.config.ViewConfig;
import org.jboss.seam.faces.view.config.ViewPattern;

@ViewConfig
public interface PageConfig {

	static enum Pages {

		@ViewPattern("/admin/configuration.xhtml")
		@Admin
		CONFIGURATION,

		@ViewPattern("/admin/privileges.xhtml")
		@Admin
		PRIVILEGES,

		@FacesRedirect
		@ViewPattern("/*")
		@AccessDeniedView("/denied.xhtml")
		@LoginView("/login.xhtml")
		ALL;
	}
}
