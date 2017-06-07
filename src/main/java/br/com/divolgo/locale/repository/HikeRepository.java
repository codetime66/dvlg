package br.com.divolgo.locale.repository;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.divolgo.locale.model.Hike;
import br.com.divolgo.locale.model.Trip;

@ApplicationScoped
public class HikeRepository {

	@PersistenceContext(unitName="hike-PU-JTA")
	private EntityManager entityManager;

	public Hike createHike(Hike hike, Trip recommendedTrip) {

		if ( recommendedTrip != null ) {
			recommendedTrip = entityManager.merge( recommendedTrip );
			hike.recommendedTrip = recommendedTrip;
			recommendedTrip.availableHikes.add( hike );
		}

		entityManager.persist( hike );
		return hike;
	}

	public Hike getHikeById(String hikeId) {
		return entityManager.find( Hike.class, hikeId );
	}

	public List<Hike> getAllHikes() {
		return entityManager.createQuery( "FROM Hike", Hike.class ).getResultList();
	}

	public List<Hike> getHikesByFromOrTo(String term) {
		return entityManager
			.createQuery( "FROM Hike WHERE start LIKE :term or destination LIKE :term", Hike.class )
			.setParameter( "term", "%" + term + "%" )
			.getResultList();
	}

	public Hike saveHike(Hike hike) {
		entityManager.persist( hike );
		return hike;
	}

	public void deleteHike(String hikeId) {
		Hike hike = entityManager.find( Hike.class, hikeId );

		if ( hike != null ) {
			if ( hike.recommendedTrip != null ) {
				hike.recommendedTrip.availableHikes.remove( hike );
			}
			entityManager.remove( hike );
		}
	}

	public List<Hike> getHikesByTripId(long tripId) {
		String query = "{ recommendedTrip_id: { $in: [" + tripId + "] } }";
		return entityManager.createNativeQuery( query, Hike.class ).getResultList();
	}
}
