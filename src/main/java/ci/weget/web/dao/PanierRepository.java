package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ci.weget.web.entites.commande.Panier;

@Repository
public interface PanierRepository extends JpaRepository<Panier, Long> {

// recupere les panier d'une personne
	@Query("select panier from Panier panier where panier.personne.id=?1")
	List<Panier> findPaniersByPersonne(long idPersonne);


}
