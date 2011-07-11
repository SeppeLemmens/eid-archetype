package ${package}.model;

import java.security.cert.X509Certificate;
import java.util.List;

import javax.ejb.Local;

import be.fedict.eid.applet.service.Identity;
import ${package}.entity.AdministratorEntity;

@Local
public interface AdministratorManager {

	boolean hasAdminRights(X509Certificate certificate, Identity identity);

	List<AdministratorEntity> listAdmins();

	void removeAdmin(String adminId);

	void approveAdmin(String adminId);
}
