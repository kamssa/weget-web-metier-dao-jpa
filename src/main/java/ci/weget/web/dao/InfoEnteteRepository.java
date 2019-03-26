package ci.weget.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ci.weget.web.entites.InfoEntete;

@Repository
public interface InfoEnteteRepository extends JpaRepository<InfoEntete, Long> {

}
