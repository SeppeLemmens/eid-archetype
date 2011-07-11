package ${package}.admin;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;

import org.jboss.ejb3.annotation.LocalBinding;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.annotations.security.Admin;
import org.jboss.seam.log.Log;

import ${package}.entity.AdministratorEntity;
import ${package}.model.AdministratorManager;

@Stateful
@Name("${admin-control-seam-components-prefix}Admins")
@LocalBinding(jndiBinding = "${admin-control-jndi-pattern-prefix}PrivilegesBean")
public class PrivilegesBean implements Privileges {

	@Logger
	private Log log;

	@EJB
	private AdministratorManager administratorManager;

	@SuppressWarnings("unused")
	@DataModel
	private List<AdministratorEntity> ${admin-control-seam-components-prefix}AdminList;

	@DataModelSelection
	private AdministratorEntity selectedAdmin;

	@Override
	@Admin
	public void delete() {
		this.log.debug("delete: #0", this.selectedAdmin.getName());
		this.administratorManager.removeAdmin(this.selectedAdmin.getId());
		initList();
	}

	@Override
	@Admin
	public void approve() {
		this.log.debug("approve: #0", this.selectedAdmin.getName());
		this.administratorManager.approveAdmin(this.selectedAdmin.getId());
		initList();
	}

	@Remove
	@Destroy
	@Override
	public void destroy() {
		this.log.debug("destroy");
	}

	@Override
	@Factory("${admin-control-seam-components-prefix}AdminList")
	@Admin
	public void initList() {
		this.${admin-control-seam-components-prefix}AdminList = this.administratorManager.listAdmins();
	}
}
