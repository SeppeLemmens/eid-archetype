package ${package}.entity.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.ejb.Ejb3Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ${package}.entity.AdministratorEntity;
import ${package}.entity.ConfigurationEntity;

public class PersistenceTest {

	private static final Log LOG = LogFactory.getLog(PersistenceTest.class);

	private EntityManager entityManager;

	@Before
	public void setUp() throws Exception {
		Class.forName("org.hsqldb.jdbcDriver");
		Ejb3Configuration configuration = new Ejb3Configuration();
		configuration.setProperty("hibernate.connection.driver_class",
				"org.hsqldb.jdbcDriver");
		configuration.setProperty("hibernate.connection.url",
				"jdbc:hsqldb:mem:poll");
		configuration.setProperty("hibernate.hbm2ddl.auto", "create");

		configuration.addAnnotatedClass(AdministratorEntity.class);
		configuration.addAnnotatedClass(ConfigurationEntity.class);

		EntityManagerFactory entityManagerFactory = configuration
				.buildEntityManagerFactory();

		this.entityManager = entityManagerFactory.createEntityManager();
		this.entityManager.getTransaction().begin();
	}

	@After
	public void tearDown() throws Exception {
		EntityTransaction entityTransaction = this.entityManager
				.getTransaction();
		LOG.debug("entity manager open: " + this.entityManager.isOpen());
		LOG.debug("entity transaction active: " + entityTransaction.isActive());
		if (entityTransaction.isActive()) {
			if (entityTransaction.getRollbackOnly()) {
				entityTransaction.rollback();
			} else {
				entityTransaction.commit();
			}
		}
		this.entityManager.close();
	}

	private void commit() {
		EntityTransaction entityTransaction = this.entityManager
				.getTransaction();
		this.entityManager.flush();
		entityTransaction.commit();
		this.entityManager.getTransaction().begin();
	}

	@Test
	public void testConfigurationEntity() throws Exception {
		// setup
		ConfigurationEntity configEntity = new ConfigurationEntity("test-name",
				"test-value");

		// operate
		this.entityManager.persist(configEntity);

		commit();

		ConfigurationEntity resultEntity = this.entityManager.find(
				ConfigurationEntity.class, "test-name");

		// verify
		assertNotNull(resultEntity);
		assertEquals("test-value", resultEntity.getValue());
	}

	@Test
	public void testAdministratorEntityCount() throws Exception {
		assertFalse(AdministratorEntity.hasAdmins(this.entityManager));

		AdministratorEntity administratorEntity = new AdministratorEntity(
				"test-id", "test-name", "test-card-number", false);
		this.entityManager.persist(administratorEntity);
		commit();

		assertTrue(AdministratorEntity.hasAdmins(this.entityManager));
	}
}
