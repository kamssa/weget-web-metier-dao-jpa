package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.ecole.Ecole;

public interface IEcoleMetier extends Imetier<Ecole, Long> {

List<Ecole> findEcoleByMc(String mc);
Ecole findEcoleByIdAbonnement(long id);
Ecole findEcoleParNom(String nom);
List<Ecole>findEcoleIdTypeEtablissement(long id);
}
