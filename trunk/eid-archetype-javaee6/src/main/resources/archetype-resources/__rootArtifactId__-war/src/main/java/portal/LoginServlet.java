package ${package}.portal;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.fedict.eid.idp.common.saml2.AuthenticationResponse;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession httpSession = request.getSession();
		AuthenticationResponse authenticationResponse = (AuthenticationResponse) httpSession
				.getAttribute("AuthenticationResponse");
		String identifier = authenticationResponse.getIdentifier();
		httpSession.setAttribute("UserId", identifier);

		Set<String> userRoles = new HashSet<String>();
		userRoles.add("user");
		httpSession.setAttribute("UserRoles", userRoles);

		String targetViewId = (String) httpSession.getAttribute("TargetViewId");
		targetViewId = targetViewId.replace(".xhtml", ".jsf");
		request.getRequestDispatcher(targetViewId).forward(request, response);
	}
}
