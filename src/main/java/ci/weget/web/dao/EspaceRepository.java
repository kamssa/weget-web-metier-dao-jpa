package ci.weget.web.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ci.weget.web.entites.espace.Espace;
import ci.weget.web.entites.personne.Personne;

@Repository
public interface EspaceRepository extends JpaRepository<Espace, Long> {

	
	Optional<Espace> findByLibelle(String libelle);

	// rechercher un bloc a partir de mot cle
	@Query("select b from Espace b where b.libelle like %:x%")
	List<Espace> getEspaceByMc(@Param("x") String mc);

	
}
