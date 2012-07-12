package ${package}.admin;

import java.security.cert.X509Certificate;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import be.fedict.eid.applet.service.Identity;
import be.fedict.eid.applet.service.cdi.BelgianCertificate;
import be.fedict.eid.applet.service.cdi.BelgianCertificate.CERTIFICATE_TYPE;

import ${package}.model.AdministratorManager;

@Named(Constants.CDI_PREFIX + "AuthenticationController")
public class AdminAuthenticationController {

	@EJB
	private AdministratorManager administratorManager;

	@Inject
	private Identity identity;

	@Inject
	@BelgianCertificate(CERTIFICATE_TYPE.AUTH)
	X509Certificate authCert;

	public String login() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		if (false == this.administratorManager.hasAdminRights(this.authCert,
				this.identity)) {
			facesContext.addMessage(null, new FacesMessage(
					FacesMessage.SEVERITY_ERROR, "Login incorrect.", null));
			return null;
		}
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		String userId = this.identity.getFirstName() + " "
				+ this.identity.getName();
		sessionMap.put("UserId", userId);
		Set<String> userRoles = new HashSet<String>();
		userRoles.add("admin");
		sessionMap.put("UserRoles", userRoles);
		String targetViewId = (String) sessionMap.get("TargetViewId");
		return targetViewId + "?faces-redirect=true";
	}

	public boolean isLoggedIn() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		String userId = (String) sessionMap.get("UserId");
		return null != userId;
	}

	public String logout() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		sessionMap.remove("UserId");
		sessionMap.remove("UserRoles");
		return "/index.xhtml";
	}
}
