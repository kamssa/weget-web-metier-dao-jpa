package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.abonnement.CursusColaire;

public interface ICursusMetier extends Imetier<CursusColaire, Long> {
	List<CursusColaire> findAllCursusColaireParCv(Long id);

}
