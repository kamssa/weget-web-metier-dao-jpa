package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ci.weget.web.entites.abonnement.TarifAbonnement;
import ci.weget.web.entites.espace.Tarif;

@Repository
public interface TarifAbonnementRepository extends JpaRepository<TarifAbonnement, Long> {
	// liste le  tarif d'un abonnemnt identifie par son id
		@Query("select t from TarifAbonnement t  where t.abonnement.id=?1")
		TarifAbonnement getTarifParIdAbonnement(long id);
}
