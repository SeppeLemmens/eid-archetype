package ${package}.model;

import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.codec.digest.DigestUtils;

import be.fedict.eid.applet.service.Identity;
import ${package}.entity.AdministratorEntity;

@Stateless
public class AdministratorManagerBean {

	@PersistenceContext
	private EntityManager entityManager;

	public boolean hasAdminRights(X509Certificate certificate, Identity identity) {
		String id = getId(certificate);
		AdministratorEntity adminEntity = this.entityManager.find(
				AdministratorEntity.class, id);
		if (null != adminEntity) {
			if (false == adminEntity.isPending()) {
				return true;
			} else {
				/*
				 * This admin is awaiting approval.
				 */
				return false;
			}
		}
		String name = identity.getFirstName() + " " + identity.getName();
		String cardNumber = identity.getCardNumber();
		if (AdministratorEntity.hasActiveAdmins(this.entityManager)) {
			/*
			 * So we register this admin as pending.
			 */
			adminEntity = new AdministratorEntity(id, name, cardNumber, true);
			this.entityManager.persist(adminEntity);
			return false;
		}
		/*
		 * Else we bootstrap the admin.
		 */
		adminEntity = new AdministratorEntity(id, name, cardNumber, false);
		this.entityManager.persist(adminEntity);
		return true;
	}

	private String getId(X509Certificate certificate) {
		PublicKey publicKey = certificate.getPublicKey();
		String id = DigestUtils.shaHex(publicKey.getEncoded());
		return id;
	}

	public List<AdministratorEntity> listAdmins() {
		return AdministratorEntity.getAdmins(this.entityManager);
	}

	public void removeAdmin(String adminId) {
		AdministratorEntity adminEntity = this.entityManager.find(
				AdministratorEntity.class, adminId);
		this.entityManager.remove(adminEntity);
	}

	public void approveAdmin(String adminId) {
		AdministratorEntity adminEntity = this.entityManager.find(
				AdministratorEntity.class, adminId);
		adminEntity.setPending(false);
	}
}
