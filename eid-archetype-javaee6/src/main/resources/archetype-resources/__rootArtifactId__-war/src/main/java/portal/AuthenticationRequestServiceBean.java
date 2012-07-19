package ${package}.portal;

import java.security.KeyStore.PrivateKeyEntry;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ${package}.model.ConfigurationBean;
import ${package}.model.ConfigurationProperty;
import be.fedict.eid.idp.sp.protocol.saml2.spi.AuthenticationRequestService;

@Stateless
@Local(AuthenticationRequestService.class)
public class AuthenticationRequestServiceBean implements
		AuthenticationRequestService {

	private static final Log LOG = LogFactory
			.getLog(AuthenticationRequestServiceBean.class);

	@EJB
	private ConfigurationBean configurationBean;

	public AuthenticationRequestServiceBean() {
		LOG.debug("constructor");
	}

	@Override
	public String getIssuer() {
		LOG.debug("getIssuer");
		return this.configurationBean
				.getValue(ConfigurationProperty.EID_IDP_ISSUER_NAME);
	}

	@Override
	public String getSPDestination() {
		LOG.debug("getSPDestination");
		return null;
	}

	@Override
	public String getIdPDestination() {
		LOG.debug("getIdPDestination");
		return this.configurationBean
				.getValue(ConfigurationProperty.EID_IDP_URL);
	}

	@Override
	public String getRelayState(Map<String, String[]> parameterMap) {
		LOG.debug("getRelayState");
		return null;
	}

	@Override
	public PrivateKeyEntry getSPIdentity() {
		LOG.debug("getSPIdentity");
		return null;
	}

	@Override
	public String getLanguage() {
		LOG.debug("getLanguage");
		return null;
	}
}
