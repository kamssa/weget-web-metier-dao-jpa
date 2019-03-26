package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.espace.Espace;

public interface IEspaceMetier extends Imetier<Espace, Long> {
Espace findByLibelle(String libelle);
List<Espace> getEspaceByMc(String mc);

}
