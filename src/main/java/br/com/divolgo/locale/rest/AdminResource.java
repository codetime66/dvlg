package br.com.divolgo.locale.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import br.com.divolgo.locale.model.Hike;
import br.com.divolgo.locale.model.Person;
import br.com.divolgo.locale.model.Section;
import br.com.divolgo.locale.model.Trip;
import br.com.divolgo.locale.repository.HikeRepository;
import br.com.divolgo.locale.repository.TripRepository;
import br.com.divolgo.locale.rest.model.ExternalHike;

@Path("/admin")
@Stateless
public class AdminResource {

	@Inject
	private HikeRepository hikeRepository;

	@Inject
	private TripRepository tripRepository;

	@PersistenceContext(unitName="hike-PU-JTA")
	private EntityManager hikeEm;

	@PersistenceContext(unitName="business")
	private EntityManager businessEm;

	public AdminResource() {
	}

	@GET
	@Path("/populate")
	public void populate() throws Exception {
		clearDatabase(businessEm);
		clearHikeAndTrips(hikeEm);

		Trip corsica = new Trip();
		corsica.name = "Corsica from north to south";
		corsica.price = 254.9;
		corsica.organizer = new Person( "Emmanuel" );
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		corsica.startDate = dateFormat.parse( "2015-04-10" );
		corsica.endDate = dateFormat.parse( "2015-04-17" );
		hikeEm.persist( corsica );
		Hike gr20North = new Hike("Calenzana", "Vizzavona");
		gr20North.recommendedTrip = corsica;
		gr20North.sections.add( new Section( "Calenzana", "Carozzu" ) );
		gr20North.sections.add( new Section( "Carozzu", "Vizzavona" ) );
		Hike gr20South = new Hike("Vizzavona", "Conza");
		gr20South.recommendedTrip = corsica;
		gr20South.sections.add( new Section( "Vizzavona", "Usciulu" ) );
		gr20South.sections.add( new Section( "Usciulu", "Conza" ) );
		hikeEm.persist( gr20North );
		hikeEm.persist( gr20South );


		Trip briceCanyon = new Trip();
		briceCanyon.name = "Brice canyon";
		briceCanyon.price = 254.9;
		briceCanyon.organizer = new Person( "Emmanuel" );
		briceCanyon.startDate = dateFormat.parse( "2015-06-13" );
		briceCanyon.endDate = dateFormat.parse( "2015-06-20" );
		hikeEm.persist( briceCanyon );
		Hike fairyLandTrail = new Hike("Fairyland Point", "Fairyland Point");
		fairyLandTrail.recommendedTrip = briceCanyon;
		fairyLandTrail.sections.add( new Section( "Fairyland Point", "Boat Mesa" ) );
		fairyLandTrail.sections.add( new Section( "Boat Mesa", "Tower Bridge" ) );
		fairyLandTrail.sections.add( new Section( "Tower Bridge", "Fairyland Point" ) );
		Hike riggsSpringTrail = new Hike("Rainbow Point", "Yovimpa Pass");
		riggsSpringTrail.recommendedTrip = briceCanyon;
		riggsSpringTrail.sections.add( new Section( "Yovimpa Pass", "The Promontory" ) );
		riggsSpringTrail.sections.add( new Section( "The Promontory", "Yovimpa Pass" ) );
		hikeEm.persist( fairyLandTrail );
		hikeEm.persist( riggsSpringTrail );

		Trip semiMarathon = new Trip();
		semiMarathon.name = "Semi-Marathon Paris Versailles";
		semiMarathon.startDate = dateFormat.parse( "2015-09-27" );
		semiMarathon.endDate = dateFormat.parse( "2015-09-27" );
		semiMarathon.organizer = new Person( "Association Paris Versailles" );
		semiMarathon.price = 5;
		hikeEm.persist( semiMarathon );
	}

	private void clearHikeAndTrips(EntityManager em) {
		List<?> all = em.createQuery( "from Hike" ).getResultList();
		for ( Hike object : (List<Hike>) all ) {
			object.recommendedTrip = null;
			em.remove( object );
		}

		all = em.createQuery( "from Trip" ).getResultList();
		for ( Object object : all ) {
			em.remove( object );
		}
	}

	private void clearDatabase(EntityManager em) {
		List<Object> all = em.createQuery( "from java.lang.Object" ).getResultList();
		for ( Object object : all ) {
			em.remove( object );
		}
	}
}
