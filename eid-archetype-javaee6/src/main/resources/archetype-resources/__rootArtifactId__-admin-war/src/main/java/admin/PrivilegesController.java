package ${package}.admin;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ${package}.entity.AdministratorEntity;
import ${package}.model.AdministratorManager;

@Named(Constants.CDI_PREFIX + "PrivilegesController")
@ConversationScoped
public class PrivilegesController implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Log LOG = LogFactory
			.getLog(PrivilegesController.class);

	@EJB
	private AdministratorManager administratorManager;

	private AdministratorEntity removeCandidate;

	@Inject
	private Conversation conversation;

	@RolesAllowed("admin")
	public String approve(AdministratorEntity administrator) {
		this.administratorManager.approveAdmin(administrator.getId());
		return "/admin/privileges";
	}

	@Produces
	@RequestScoped
	@Named(Constants.CDI_PREFIX + "AdminList")
	@RolesAllowed("admin")
	public List<AdministratorEntity> initAdminList() {
		return this.administratorManager.listAdmins();
	}

	@RolesAllowed("admin")
	public void removeCandidate(ActionEvent actionEvent) {
		UIComponent component = actionEvent.getComponent();
		Map<String, Object> attributes = component.getAttributes();
		if (this.conversation.isTransient()) {
			this.conversation.begin();
		}
		this.removeCandidate = (AdministratorEntity) attributes
				.get("selectedAdmin");
		LOG.debug("remove candidate: " + this.removeCandidate.getId());
	}

	@RolesAllowed("admin")
	public void removeAdmin(ActionEvent actionEvent) {
		LOG.debug("remove admin: " + this.removeCandidate.getId());
		this.administratorManager.removeAdmin(this.removeCandidate.getId());
		FacesContext facesContext = FacesContext.getCurrentInstance();
		facesContext.addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_INFO, "Removed admin "
						+ this.removeCandidate.getName(), null));
		this.removeCandidate = null;
		this.conversation.end();
	}
}
