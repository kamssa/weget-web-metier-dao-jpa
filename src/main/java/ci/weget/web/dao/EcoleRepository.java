package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ci.weget.web.entites.ecole.Ecole;
import ci.weget.web.entites.ecole.TypeEtablissement;

@Repository
public interface EcoleRepository extends JpaRepository<Ecole, Long> {

	
	// rechercher une ecole par mot cle
	@Query("select e from Ecole e where e.nom like %:x%")
	List<Ecole> findEcoleByMc(@Param("x") String mc);

	// recuperer abonnement d'une ecole
	@Query("select e from Ecole e  where e.abonnement.id=?1")
	Ecole findEcoleByIdAbonnement(long id);

	// recuperer une ecole par son nom
	@Query("select e from Ecole e where e.nom=?1")
	Ecole findEcoleByNom(String nom);

	// recupere les ecoles par le type de l'etablissement et abonnement actif
	@Query("select e from Ecole e  where e.typeEtablissement.id=?1 AND e.abonnement.active=true")
	List<Ecole> findEcoleByTypeEtablissement(long id);

}
