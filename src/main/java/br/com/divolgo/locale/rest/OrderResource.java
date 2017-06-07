package br.com.divolgo.locale.rest;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import br.com.divolgo.locale.model.business.Order;
import br.com.divolgo.locale.repository.business.OrderRepository;

@Path("/orders")
@Stateless
public class OrderResource {

	@Inject
	private OrderRepository orderRepository;

	public OrderResource() {
	}

	@POST
	@Path("/")
	@Consumes("application/json")
	@Produces("application/json")
	public Order createOrder(Order order) {
		order = orderRepository.createOrder(order);
		return order;
	}

	@GET
	@Path("/")
	@Produces("application/json")
	public List<Order> getAllOrders() {
		return orderRepository.getAllOrders();
	}
}
