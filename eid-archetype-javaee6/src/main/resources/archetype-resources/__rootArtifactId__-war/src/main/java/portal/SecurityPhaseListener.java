package ${package}.portal;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import javax.faces.application.Application;
import javax.faces.application.NavigationHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class SecurityPhaseListener implements PhaseListener {

	private static final long serialVersionUID = 1L;

	private static final Log LOG = LogFactory
			.getLog(SecurityPhaseListener.class);
	
	@Override
	public void afterPhase(PhaseEvent event) {
		FacesContext facesContext = event.getFacesContext();
		UIViewRoot viewRoot = facesContext.getViewRoot();
		String viewId = viewRoot.getViewId();
		if (viewId.matches("/user/.*")) {
			ExternalContext externalContext = facesContext.getExternalContext();
			Map<String, Object> sessionMap = externalContext.getSessionMap();
			String userId = (String) sessionMap.get("UserId");
			if (null == userId) {
				sessionMap.put("TargetViewId", viewId);
				String contextPath = externalContext.getRequestContextPath();
				try {
					externalContext.redirect(contextPath + "/eid-idp-request");
				} catch (IOException e) {
					LOG.error("error redirecting to /eid-idp-request: "
							+ e.getMessage());
				}
			} else {
				Set<String> userRoles = (Set<String>) sessionMap
						.get("UserRoles");
				if (false == hasRole("admin", userRoles)) {
					if (false == redirect(facesContext, "/denied.xhtml")) {
						throw new SecurityException("access denied");
					}
				}
			}
		}
	}

	private boolean redirect(FacesContext facesContext, String viewId) {
		if (facesContext.getResponseComplete()) {
			return false;
		}
		Application application = facesContext.getApplication();
		NavigationHandler navigationHandler = application
				.getNavigationHandler();
		navigationHandler.handleNavigation(facesContext, null, viewId
				+ "?faces-redirect=true");
		facesContext.renderResponse();
		return true;
	}

	private boolean hasRole(String expectedRole, Set<String> actualRoles) {
		if (null == actualRoles) {
			return false;
		}
		if (actualRoles.contains(expectedRole)) {
			return true;
		}
		return false;
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		// empty
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.ANY_PHASE;
	}
}
