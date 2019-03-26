package ci.weget.web.dao;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ci.weget.web.entites.abonnement.Abonnement;
import ci.weget.web.entites.personne.Personne;


@Repository
public interface AbonnementRepository extends JpaRepository<Abonnement, Long> {

	// recupere les abonnement en fonction du libelle de l'espace
	@Query("select ab from Abonnement ab  where ab.espace.libelle=?1")
	List<Abonnement> findAbonnementByLibelleEspace(String libelle);

	// recupere les abonnement par id de espace
	@Query("select ab from Abonnement ab  where ab.espace.id=?1")
	List<Abonnement> findAbonnementByIdEspace(long id);

	// ramener les abonnements d'une personne a partir de son identifianr
	@Query("select ab from Abonnement ab where ab.membre.id=?1")
	List<Abonnement> findAbonnementByIdPersonne(long id);

	// ramener tous les abonnements d'une personnes par login
	@Query("select ab from Abonnement  ab where ab.membre.login=?1")
	List<Abonnement> findAbonnementByLogin(String login);

	// ramener les abonnements a partir de espace et de personne
	@Query("select ab from Abonnement ab left join fetch ab.espace e left join fetch ab.membre p where p.id=?1 AND  e.id=?2")
	Abonnement findAbonnementByIdPersonneAndIdEspace(long idPersonne, long idEspace);

	// rechercher un abonnement par ville
	@Query("select ab from Abonnement ab  where ab.membre.adresse.ville=?1")
	List<Abonnement> findAbonnementByVille(String ville);

	// rechercher un abonnement par type espace
	@Query("select ab from Abonnement ab  where ab.espace.typeEspace=?1")
	List<Abonnement> findAbonnemntByTypeEspace(String type);

	// ramener les Abonnes en une seul occurence
	@Query("select DISTINCT  ab from Abonnement ab where ab.membre.id=?1")
	List<Personne> findDistinctAbonnement(Long id);

	// ramener les abonnes d'un mois donnees
	@Query("select ab from Abonnement ab  where ab.dateAbonnement=?1")
	List<Abonnement> findAbonnementByMois(LocalDate mois);

	// ramener les abonnes bani
	@Query("select ab from Abonnement ab  where ab.utlisateurBanni=true")
	List<Abonnement> findAbonnementBani();

}
