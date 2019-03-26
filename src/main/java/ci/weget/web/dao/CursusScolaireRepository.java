package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ci.weget.web.entites.abonnement.CursusColaire;

@Repository
public interface CursusScolaireRepository extends JpaRepository<CursusColaire, Long> {
	// ramener les Cursus d'un cv
	@Query("select c from CursusColaire c  where c.cvPersonne.id=?1")
	List<CursusColaire> findAllCursusColaireParCv(Long id);
}
