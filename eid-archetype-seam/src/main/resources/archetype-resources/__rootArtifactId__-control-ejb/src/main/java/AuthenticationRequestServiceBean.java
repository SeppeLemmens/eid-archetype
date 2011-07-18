package ${package};

import java.security.KeyStore.PrivateKeyEntry;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.security.jacc.PolicyContext;
import javax.security.jacc.PolicyContextException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.ejb3.annotation.LocalBinding;

import be.fedict.eid.idp.sp.protocol.saml2.spi.AuthenticationRequestService;
import ${package}.model.ConfigurationProperty;
import ${package}.model.Configuration;

@Stateless
@Local(AuthenticationRequestService.class)
@LocalBinding(jndiBinding = "${control-jndi-pattern-prefix}AuthenticationRequestServiceBean")
public class AuthenticationRequestServiceBean implements
		AuthenticationRequestService {

	private static final Log LOG = LogFactory
			.getLog(AuthenticationRequestServiceBean.class);

	@EJB
	private Configuration configuration;

	@Override
	public String getIdPDestination() {
		String idpUrl = this.configuration.getValue(ConfigurationProperty.EID_IDP_URL);
		LOG.debug("IdP URL: " + idpUrl);
		return idpUrl;
	}

	@Override
	public String getRelayState(Map<String, String[]> parameterMap) {
		/*
		 * Because the JBoss Seam redirect component is conversation scoped we
		 * need to preserve the conversation. We use the SAML Relay State for
		 * this.
		 */
		String conversationId = parameterMap.get("conversationId")[0];
		LOG.debug("conversation id: " + conversationId);
		return conversationId;
	}

	@Override
	public PrivateKeyEntry getSPIdentity() {
		return null;
	}

	@Override
	public String getIssuer() {
		String idpIssuerName = this.configuration
				.getValue(ConfigurationProperty.EID_IDP_ISSUER_NAME);
		return idpIssuerName;
	}

	@Override
	public String getLanguage() {
		String language = LanguageSelectorBean.getLanguage();
		return language;
	}

	@Override
	public String getSPDestination() {
		HttpServletRequest httpServletRequest;
		try {
			httpServletRequest = (HttpServletRequest) PolicyContext
					.getContext("javax.servlet.http.HttpServletRequest");
		} catch (PolicyContextException e) {
			throw new RuntimeException("JACC error: " + e.getMessage());
		}
		String spDestination = httpServletRequest.getScheme() + "://"
				+ httpServletRequest.getServerName() + ":"
				+ httpServletRequest.getServerPort()
				+ httpServletRequest.getContextPath()
				+ "/authentication-response";
		return spDestination;
	}
}
