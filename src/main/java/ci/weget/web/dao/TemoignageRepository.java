package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ci.weget.web.entites.ecole.Temoignage;

@Repository
public interface TemoignageRepository extends JpaRepository<Temoignage, Long> {
	// ramener un temoignage a partir de son identifiant
	@Query("select t from Temoignage t where t.id=?1")
	Temoignage getByid(Long id);
/*
	// ramener les  temoignage a d'un sous block
	@Query("select t from Temoignage t where t.sousBlock.id=?1")
	List<Temoignage> getTemoignageByIdSousBlock(Long id);*/
}
