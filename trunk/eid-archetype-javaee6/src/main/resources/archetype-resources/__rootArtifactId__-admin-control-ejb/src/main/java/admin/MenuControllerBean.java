package ${package}.admin;

import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

@Stateful
@Named(Constants.JNDI_PREFIX + "MenuController")
@SessionScoped
public class MenuControllerBean implements MenuController {

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
