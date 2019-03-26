package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.ecole.Ecole;
import ci.weget.web.entites.ecole.TypeEtablissement;

public interface ITypeEtablissementMetier extends Imetier<TypeEtablissement, Long> {
	TypeEtablissement rechercheParLibelle(String libelle);
   
}
