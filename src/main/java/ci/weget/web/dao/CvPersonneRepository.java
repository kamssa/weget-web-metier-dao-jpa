package ci.weget.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ci.weget.web.entites.abonnement.CvPersonne;

import java.util.Optional;

@Repository
public interface CvPersonneRepository extends JpaRepository<CvPersonne, Long>{
	// ramener les espaces a partir de id de abonnement
		@Query("select cv from CvPersonne cv  where cv.abonnement.id=?1")
		CvPersonne getCvPersonneByIdAbonnement(long id);
		Optional<CvPersonne> findByIdAndAbonnementId(Long id, Long AbonnementId);
}
