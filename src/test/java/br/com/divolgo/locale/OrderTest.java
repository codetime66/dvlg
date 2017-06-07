package br.com.divolgo.locale;

import static org.fest.assertions.Assertions.assertThat;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.divolgo.locale.model.business.Order;
import br.com.divolgo.locale.model.business.Customer;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class OrderTest {

	private EntityManager entityManager;

	@Rule
	public ExpectedException exception = ExpectedException.none();

	@Before
	public void setupEntityManager() {
		EntityManagerFactory emf =
				Persistence.createEntityManagerFactory( "hike-PU" );
		entityManager = emf.createEntityManager();
	}

	@Test
	public void simpleEntityTest() {
		entityManager.getTransaction().begin();

                Customer customer = new Customer();
                customer.name="john";
                customer.email="john@bla.com";
                Order order = new Order();
                order.number="123";
                order.tripId=23213l;
                order.customer=customer;
		entityManager.persist( order );

		entityManager.getTransaction().commit();

		entityManager.getTransaction().begin();
		order = entityManager.find( Order.class, order.id );

		assertThat( order ).isNotNull();
		assertThat( order.number ).isEqualTo( "123" );

		entityManager.getTransaction().commit();
	}
}
