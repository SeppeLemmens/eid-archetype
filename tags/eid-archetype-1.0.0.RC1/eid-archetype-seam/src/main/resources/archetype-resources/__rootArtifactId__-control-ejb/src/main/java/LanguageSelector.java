package ${package};

import java.util.List;

import javax.ejb.Local;
import javax.faces.model.SelectItem;

@Local
public interface LanguageSelector {

	String getLocaleString();

	void setLocaleString(java.lang.String localeString);

	List<SelectItem> getSupportedLocales();
}
