package ${package};

import javax.ejb.Local;

@Local
public interface MenuController {

	String getSelectedChild();

	void setSelectedChild(String selectedChild);

	void destroy();
}
