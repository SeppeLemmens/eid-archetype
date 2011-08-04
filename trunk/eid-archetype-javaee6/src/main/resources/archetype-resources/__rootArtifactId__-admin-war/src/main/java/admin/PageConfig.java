package ${package}.admin;

import org.jboss.seam.faces.rewrite.FacesRedirect;
import org.jboss.seam.faces.security.AccessDeniedView;
import org.jboss.seam.faces.security.LoginView;
import org.jboss.seam.faces.view.config.ViewConfig;
import org.jboss.seam.faces.view.config.ViewPattern;

@ViewConfig
public interface PageConfig {

	static enum Pages {

		@ViewPattern("/admin/*")
		@Admin
		ADMIN_PAGES,

		@FacesRedirect
		@ViewPattern("/*")
		@AccessDeniedView("/denied.xhtml")
		@LoginView("/login.xhtml")
		ALL;
	}
}
