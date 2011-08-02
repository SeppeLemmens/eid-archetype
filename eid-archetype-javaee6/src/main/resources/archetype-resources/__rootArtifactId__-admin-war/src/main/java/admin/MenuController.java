package ${package}.admin;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import java.io.Serializable;

@Named(Constants.CDI_PREFIX + "MenuController")
@SessionScoped
public class MenuController implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String selectedChild;

	public String getSelectedChild() {
		return this.selectedChild;
	}

	public void setSelectedChild(String selectedChild) {
		this.selectedChild = selectedChild;
	}
}
