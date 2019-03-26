package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ci.weget.web.entites.ecole.FlashInfo;

@Repository
public interface FlashInfoRepository extends JpaRepository<FlashInfo, Long> {
	
	// ramener les abonnes d'un block par libelle
	@Query("select f from FlashInfo f  where f.ecole.id=?1")
	List<FlashInfo> findAllFlashInfoByEcole(Long id);
}
