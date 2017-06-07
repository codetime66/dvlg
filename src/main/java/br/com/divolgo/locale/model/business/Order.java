package br.com.divolgo.locale.model.business;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author codetime
 */
@Entity
@Table(name="TripOrder")
public class Order {
	@Id
	@GeneratedValue
	public Long id;

	public String number;
	public long tripId;
	@Embedded
	public Customer customer;
}
