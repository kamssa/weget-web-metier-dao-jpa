package ci.weget.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ci.weget.web.entites.abonnement.CvAbonnement;

@Repository
public interface CvAbonnementRepository extends JpaRepository<CvAbonnement, Long> {
	// recupere les abonnement a partir du cv
	@Query("select cvAb from CvAbonnement cvAb where cvAb.idCvPersonne=?1")
	CvAbonnement findCvAbonnementParIdCv(Long id);

	// recupere les cv a partir de abonnement
	@Query("select cvAb from CvAbonnement cvAb where cvAb.idAbonnement=?1")
	CvAbonnement findCvAbonnementParIdAbonnement(Long id);
	
	// recupere les cv a partir de specialite
		@Query("select cvAb from CvAbonnement cvAb where cvAb.specialite=?1")
		CvAbonnement findCvAbonnementParSpecialite(String specialite);
}
