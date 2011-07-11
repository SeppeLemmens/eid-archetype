package ${package};

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.jboss.ejb3.annotation.LocalBinding;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.faces.FacesMessages;
import org.jboss.seam.international.LocaleSelector;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.joda.time.DateTime;
import org.joda.time.Years;

import be.fedict.eid.idp.common.AttributeConstants;
import be.fedict.eid.idp.common.saml2.AuthenticationResponse;

@Name("${control-seam-components-prefix}Authenticator")
@Stateless
@LocalBinding(jndiBinding = "${control-jndi-pattern-prefix}AuthenticatorBean")
public class AuthenticatorBean implements Authenticator {

	public static final String HMAC_ALGO = "HmacSHA1";

	@Logger
	private Log log;

	@In
	Credentials credentials;

	@In
	BelgianIdentity identity;

	@In(value = "AuthenticationResponse", scope = ScopeType.SESSION)
	private AuthenticationResponse authenticationResponse;

	@Override
	public boolean authenticate() {
		String userIdentifier = this.authenticationResponse.getIdentifier();
		this.log.debug("user identifier: #0", userIdentifier);

		// assign roles
		this.identity.addRole("user");

		// extract attributes from the SAML
		Map<String, Object> attributeMap = this.authenticationResponse
				.getAttributeMap();
		String givenName = (String) attributeMap
				.get(AttributeConstants.FIRST_NAME_CLAIM_TYPE_URI);
		this.identity.setGivenName(givenName);
		String name = (String) attributeMap
				.get(AttributeConstants.LAST_NAME_CLAIM_TYPE_URI);
		this.identity.setName(name);

		this.identity.setIdentifier(userIdentifier);

		return true;
	}

	@Override
	public void loginSuccessfull() {
		this.log.debug("loginSuccessfull");
		FacesMessages facesMessages = FacesMessages.instance();
		facesMessages.clearGlobalMessages();
		facesMessages.clear();

		// TODO create user account in database here
	}
}