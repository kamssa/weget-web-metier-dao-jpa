package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ci.weget.web.entites.espace.Tarif;

@Repository
public interface TarifRepository extends JpaRepository<Tarif, Long> {
	// liste des tarif d'un espace identifie par son id
	@Query("select t from Tarif t  where t.espace.id=?1")
	List<Tarif> getTarifByEspace(long id);
}
