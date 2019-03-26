package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ci.weget.web.entites.abonnement.Experiences;

@Repository
public interface ExperienceRepository extends JpaRepository<Experiences, Long> {
	// ramener les experiences d'un cv
	@Query("select e from Experiences e  where e.cvPersonne.id=?1")
	List<Experiences> findAllExperiencesByCv(Long id);
}
