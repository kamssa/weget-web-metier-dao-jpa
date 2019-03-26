package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ci.weget.web.entites.DetailAbonnement;
import ci.weget.web.entites.TypeEtablissement;

public interface DetailAbonnementRepository extends JpaRepository<DetailAbonnement, Long> {

	// retrouver un sous block a partir de son libelle
	@Query("select da from DetailAbonnement da where da.id=?1")
	DetailAbonnement findDetailAonnementyId(Long id);

	// rechercher un sous bloc a partir de son libelle
	@Query("select da from DetailAbonnement da where da.nom like %:x%")
	List<DetailAbonnement> chercherDetailAbonnementParMc(@Param("x") String mc);

	// ramener les sous blocks a partir de id de detail block
	@Query("select da from DetailAbonnement da  where da.abonnement.id=?1")
	DetailAbonnement findDetailAbonnementParIdDetailBlock(Long id);

	// rechercher les sous blocks par block
	@Query("select da from DetailAbonnement da  where da.idBlock=?1")
	List<DetailAbonnement> findDetailAbonnementParIdBlock(Long id);

	// rechercher les sous blocks par libelle
	@Query("select da from DetailAbonnement da  where da.nom=?1")
	DetailAbonnement findDetailAbonnementParNom(String nom);

	// rechercher les Deatail abonnement  par typeEtablissemnt
	@Query("select da from DetailAbonnement da  where da.typeEtablissement=?1")
	List<DetailAbonnement> findDetailAbonnementParIdEta(Long id);

}
