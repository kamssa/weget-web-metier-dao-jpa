package ci.weget.web.dao.publicite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.publicite.Position;

public interface PositionRepository extends JpaRepository<Position, Long> {
	// ramener une liste de detail block quand on connait l'id du block
	@Query("select p from Position p  where p.libelle=?1")
	Position findAllPositionParNom(String libelle);
}
