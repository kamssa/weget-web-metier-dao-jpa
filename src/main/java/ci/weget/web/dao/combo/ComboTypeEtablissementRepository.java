package ci.weget.web.dao.combo;

import org.springframework.data.jpa.repository.JpaRepository;

import ci.weget.web.entites.combo.ComboTypeEtablisement;
import ci.weget.web.entites.ecole.TypeEtablissement;

public interface ComboTypeEtablissementRepository extends JpaRepository<ComboTypeEtablisement, Long> {

}
