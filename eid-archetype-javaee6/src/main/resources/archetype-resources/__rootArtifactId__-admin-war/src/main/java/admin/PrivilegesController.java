package ${package}.admin;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import ${package}.admin.Constants;
import ${package}.admin.RolesAllowed;
import ${package}.entity.AdministratorEntity;
import ${package}.entity.AdministratorEntity;
import ${package}.model.AdministratorManager;

@Named(Constants.CDI_PREFIX + "PrivilegesController")
@RequestScoped
public class PrivilegesController {

	@EJB
	private AdministratorManager administratorManager;

	@RolesAllowed("admin")
	public String approve(AdministratorEntity administrator) {
		this.administratorManager.approveAdmin(administrator.getId());
		return "/admin/privileges";
	}

	@RolesAllowed("admin")
	public String delete(AdministratorEntity administrator) {
		this.administratorManager.removeAdmin(administrator.getId());
		return "/admin/privileges";
	}

	@Produces
	@RequestScoped
	@Named(Constants.CDI_PREFIX + "AdminList")
	@RolesAllowed("admin")
	public List<AdministratorEntity> initAdminList() {
		return this.administratorManager.listAdmins();
	}
}
