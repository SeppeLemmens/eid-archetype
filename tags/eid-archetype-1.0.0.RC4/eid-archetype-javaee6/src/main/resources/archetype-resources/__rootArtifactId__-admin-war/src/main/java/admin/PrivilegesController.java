package ${package}.admin;

import java.util.List;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

import org.richfaces.component.UIExtendedDataTable;

import ${package}.entity.AdministratorEntity;
import ${package}.model.AdministratorManager;

@Named(Constants.CDI_PREFIX + "PrivilegesController")
@RequestScoped
public class PrivilegesController {

	@EJB
	private AdministratorManager administratorManager;

	private UIExtendedDataTable dataTable;

	@Admin
	public String approve() {
		AdministratorEntity selectedAdmin = (AdministratorEntity) this.dataTable
				.getRowData();
		this.administratorManager.approveAdmin(selectedAdmin.getId());
		return "/admin/privileges";
	}

	@Admin
	public String delete() {
		AdministratorEntity selectedAdmin = (AdministratorEntity) this.dataTable
				.getRowData();
		this.administratorManager.removeAdmin(selectedAdmin.getId());
		return "/admin/privileges";
	}

	@Produces
	@RequestScoped
	@Named(Constants.CDI_PREFIX + "AdminList")
	@Admin
	public List<AdministratorEntity> initAdminList() {
		return this.administratorManager.listAdmins();
	}

	public UIExtendedDataTable getDataTable() {
		return this.dataTable;
	}

	public void setDataTable(UIExtendedDataTable dataTable) {
		this.dataTable = dataTable;
	}
}
