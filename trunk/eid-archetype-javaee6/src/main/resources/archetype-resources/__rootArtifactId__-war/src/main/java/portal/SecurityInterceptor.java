package ${package}.portal;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@RolesAllowed
@Interceptor
public class SecurityInterceptor implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@AroundInvoke
	public Object securityAspect(InvocationContext invocationContext)
			throws Exception {
		Method method = invocationContext.getMethod();
		RolesAllowed rolesAllowedAnnotation = method
				.getAnnotation(RolesAllowed.class);
		if (null == rolesAllowedAnnotation) {
			Class<?> targetClass = invocationContext.getMethod()
					.getDeclaringClass();
			rolesAllowedAnnotation = targetClass
					.getAnnotation(RolesAllowed.class);
		}
		String[] allowedRoles = rolesAllowedAnnotation.value();
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		Set<String> actualRoles = (Set<String>) sessionMap.get("UserRoles");
		checkRoles(allowedRoles, actualRoles);
		return invocationContext.proceed();
	}

	private void checkRoles(String[] allowedRoles, Set<String> actualRoles) {
		for (String allowedRole : allowedRoles) {
			if (actualRoles.contains(allowedRole)) {
				return;
			}
		}
		throw new SecurityException("missing allowed role");
	}
}
