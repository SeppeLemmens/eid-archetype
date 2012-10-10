package ${package}.portal;

import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named("${control-cdi-components-prefix}AuthenticationController")
public class AuthenticationController {

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
