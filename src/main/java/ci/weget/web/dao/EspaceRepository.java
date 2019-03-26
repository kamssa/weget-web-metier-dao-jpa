package ci.weget.web.dao;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ci.weget.web.entites.Block;
import ci.weget.web.entites.Personne;

public interface BlocksRepository extends JpaRepository<Block, Long> {

	// retrouver un block a partir de son libelle
	@Query("select b from Block b where b.libelle=?1")
	Block findByLibelle(String libelle);

	// rechercher un bloc a partir de mot cle
	@Query("select b from Block b where b.libelle like %:x%")
	List<Block> chercherBlockParMc(@Param("x") String mc);

	// ramener un block a partir de son identifiant
	@Query("select b from Block b where b.id=?1")
	Block getByid(Long id);

	Optional<Block> findById(Long id);

}
