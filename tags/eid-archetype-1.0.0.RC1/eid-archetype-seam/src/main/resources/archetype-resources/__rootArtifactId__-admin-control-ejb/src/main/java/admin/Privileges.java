package ${package}.admin;

import javax.ejb.Local;

@Local
public interface Privileges {

	/*
	 * Actions.
	 */
	void delete();

	void approve();

	/*
	 * Factories.
	 */
	void initList();

	/*
	 * Lifecycle.
	 */
	void destroy();
}
