package ${package};

import javax.ejb.Local;

@Local
public interface Authenticator {

	boolean authenticate();

	void loginSuccessfull();
}
