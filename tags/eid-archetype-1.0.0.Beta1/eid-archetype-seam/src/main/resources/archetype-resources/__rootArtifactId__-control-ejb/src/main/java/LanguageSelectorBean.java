package ${package};

import java.util.List;

import javax.ejb.Stateless;
import javax.faces.model.SelectItem;
import javax.security.jacc.PolicyContext;
import javax.security.jacc.PolicyContextException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jboss.ejb3.annotation.LocalBinding;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.international.LocaleSelector;

@Stateless
@Name("${control-seam-components-prefix}LanguageSelector")
@LocalBinding(jndiBinding = "${control-jndi-pattern-prefix}LanguageSelectorBean")
public class LanguageSelectorBean implements LanguageSelector {

	private static final String LANGUAGE_SESSION_ATTRIBUTE = "LanguageSelectorBean.language";

	@In
	private LocaleSelector localeSelector;

	@SuppressWarnings("unused")
	@In(value = LANGUAGE_SESSION_ATTRIBUTE, required = false, scope = ScopeType.SESSION)
	@Out(value = LANGUAGE_SESSION_ATTRIBUTE, required = false, scope = ScopeType.SESSION)
	private String language;

	@Override
	public String getLocaleString() {
		this.language = this.localeSelector.getLanguage();
		return this.localeSelector.getLocaleString();
	}

	@Override
	public void setLocaleString(String localeString) {
		this.localeSelector.setLocaleString(localeString);
		this.localeSelector.select();
		this.language = this.localeSelector.getLanguage();
	}

	@Override
	public List<SelectItem> getSupportedLocales() {
		List<SelectItem> supportedLocales = this.localeSelector
				.getSupportedLocales();
		for (SelectItem supportedLocale : supportedLocales) {
			String label = supportedLocale.getLabel();
			if (Character.isLowerCase(label.charAt(0))) {
				label = Character.toUpperCase(label.charAt(0))
						+ label.substring(1);
				supportedLocale.setLabel(label);
			}
		}
		return supportedLocales;
	}
	
	public static String getLanguage() {
		HttpServletRequest httpServletRequest;
		try {
			httpServletRequest = (HttpServletRequest) PolicyContext
					.getContext("javax.servlet.http.HttpServletRequest");
		} catch (PolicyContextException e) {
			throw new RuntimeException("JACC error: " + e.getMessage());
		}
		HttpSession httpSession = httpServletRequest.getSession();
		String language = (String) httpSession
				.getAttribute(LANGUAGE_SESSION_ATTRIBUTE);
		return language;
	}
}
