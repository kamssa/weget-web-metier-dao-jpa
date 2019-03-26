package ci.weget.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ci.weget.web.entites.commande.Commande;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {
	

	// recupere paiement a partir de son id
	@Query("select c from Commande c where c.personne.id=?1")
	Commande findByIdPersonne(long id);
}
