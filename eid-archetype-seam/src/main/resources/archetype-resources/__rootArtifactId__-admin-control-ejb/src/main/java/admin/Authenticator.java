package ${package}.admin;

import javax.ejb.Local;

@Local
public interface Authenticator {

	boolean authenticate();
}
