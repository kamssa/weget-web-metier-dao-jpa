package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.espace.Espace;
import ci.weget.web.entites.espace.Tarif;

public interface ITarifMetier extends Imetier<Tarif, Long>{
	List<Tarif> getTarifByIdEspace(long id);
	 Tarif  ajouterEspace(Tarif t, Espace b);
}
