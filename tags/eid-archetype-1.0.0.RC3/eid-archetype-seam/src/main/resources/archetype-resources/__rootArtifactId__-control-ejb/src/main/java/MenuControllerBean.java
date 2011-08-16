package ${package};

import javax.ejb.Remove;
import javax.ejb.Stateful;

import org.jboss.ejb3.annotation.LocalBinding;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.log.Log;

@Stateful
@Name("${control-seam-components-prefix}MenuController")
@LocalBinding(jndiBinding = Constants.JNDI_PREFIX + "MenuControllerBean")
@Scope(ScopeType.SESSION)
public class MenuControllerBean implements MenuController {

	@Logger
	private Log log;

	private String selectedChild;

	@Override
	public String getSelectedChild() {
		return this.selectedChild;
	}

	@Override
	public void setSelectedChild(String selectedChild) {
		this.selectedChild = selectedChild;
	}

	@Remove
	@Destroy
	@Override
	public void destroy() {
		this.log.debug("destroy");
	}
}
