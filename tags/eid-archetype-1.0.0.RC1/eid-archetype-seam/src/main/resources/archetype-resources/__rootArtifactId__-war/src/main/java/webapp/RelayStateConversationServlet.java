package ${package}.webapp;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import be.fedict.eid.idp.common.saml2.AuthenticationResponse;

/**
 * Servlet to recover the JBoss Seam conversation from the SAML relay state.
 */
public class RelayStateConversationServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private String responseSessionAttribute;

	private String redirectPage;

	@Override
	public void init(ServletConfig config) throws ServletException {
		this.responseSessionAttribute = getRequiredInitParameter(
				"ResponseSessionAttribute", config);
		this.redirectPage = getRequiredInitParameter("RedirectPage", config);
	}

	private String getRequiredInitParameter(String parameterName,
			ServletConfig config) throws ServletException {
		String value = config.getInitParameter(parameterName);
		if (null == value) {
			throw new ServletException(parameterName
					+ " init-param is required");
		}
		return value;
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession httpSession = request.getSession();
		AuthenticationResponse authenticationResponse = (AuthenticationResponse) httpSession
				.getAttribute(this.responseSessionAttribute);
		String relayState = authenticationResponse.getRelayState();
		String conversationId = relayState;

		response.sendRedirect(request.getContextPath() + this.redirectPage
				+ "?conversationId=" + conversationId);
	}
}
