package ci.weget.web.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ci.weget.web.entites.HistoriquePanier;

@Repository
public interface HistoriquePanierRepository extends JpaRepository<HistoriquePanier, Long> {

}
