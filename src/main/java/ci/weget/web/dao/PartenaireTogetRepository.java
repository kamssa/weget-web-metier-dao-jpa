package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ci.weget.web.entites.Chiffre;
import ci.weget.web.entites.Partenaire;

public interface PartenaireRepository extends JpaRepository<Partenaire, Long> {
	// ramener un patenaire a partir de son identifiant
	@Query("select p from Partenaire p where p.id=?1")
	Partenaire getByid(Long id);

	/*// ramener les patenaire a d'un sous block
	@Query("select p from Chiffre p where p.sousBlock.id=?1")
	List<Partenaire> getPartenaireByIdSousBlock(Long id);*/
}
