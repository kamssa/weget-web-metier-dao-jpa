package ci.weget.web.dao.publicite;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.publicite.Publicite;

public interface PubliciteRepository extends JpaRepository<Publicite, Long> {
	// ramener une liste de detail block quand on connait l'id du block
	@Query("select p from Publicite p  where p.position.id=?1")
	List<Publicite> findAllPubliciteParIdPosition(Long id);

	// ramener une liste de detail block quand on connait l'id du block
	@Query("select p from Publicite p  where p.page=?1")
	List<Publicite> findAllPubliciteParPage(String page);
}
