package ${package}.admin;

import javax.ejb.Remove;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import java.io.Serializable;

@Named(Constants.CDI_PREFIX + "MenuController")
@SessionScoped
public class MenuController implements Serializable {

	private String selectedChild;

	@Override
	public String getSelectedChild() {
		return this.selectedChild;
	}

	@Override
	public void setSelectedChild(String selectedChild) {
		this.selectedChild = selectedChild;
	}

	@Override
	@Remove
	public void destroy() {
	}
}
