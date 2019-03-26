package ci.weget.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ci.weget.web.entites.PartenaireToget;

@Repository
public interface PartenaireTogetRepository extends JpaRepository<PartenaireToget, Long> {
	
}
