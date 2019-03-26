package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ci.weget.web.entites.ecole.Formation;

@Repository
public interface FormationRepository extends JpaRepository<Formation, Long> {
	
	// ramener une formation a par sous block
	@Query("select f from Formation f where f.ecole.id=?1")
	List<Formation> getFormationByEcole(Long id);

}
