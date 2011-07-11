package ${package}.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Query;
import javax.persistence.Table;

@Entity
@Table(name = Constants.DATABASE_TABLE_PREFIX + "admin")
@NamedQueries({
		@NamedQuery(name = AdministratorEntity.COUNT_ALL, query = "SELECT COUNT(*) FROM AdministratorEntity"),
		@NamedQuery(name = AdministratorEntity.ALL, query = "SELECT admin FROM AdministratorEntity AS admin") })
public class AdministratorEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String COUNT_ALL = "${jpa-named-query-prefix}.count.all";

	public static final String ALL = "${jpa-named-query-prefix}.admin.all";

	private String id;

	private String name;

	private String cardNumber;

	private boolean pending;

	public AdministratorEntity(String id, String name, String cardNumber,
			boolean pending) {
		this.id = id;
		this.name = name;
		this.cardNumber = cardNumber;
		this.pending = pending;
	}

	public AdministratorEntity() {
		super();
	}

	@Id
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(nullable = false)
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(nullable = false)
	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public boolean isPending() {
		return this.pending;
	}

	public void setPending(boolean pending) {
		this.pending = pending;
	}

	public static boolean hasAdmins(EntityManager entityManager) {
		Query query = entityManager.createNamedQuery(COUNT_ALL);
		Long count = (Long) query.getSingleResult();
		return 0 != count;
	}

	@SuppressWarnings("unchecked")
	public static List<AdministratorEntity> getAdmins(
			EntityManager entityManager) {
		Query query = entityManager.createNamedQuery(ALL);
		return query.getResultList();
	}
}
