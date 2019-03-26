package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.commande.LigneCommande;
import ci.weget.web.entites.espace.Espace;
import ci.weget.web.entites.personne.Personne;

public interface ILigneCommandeMetier extends Imetier<LigneCommande, Long> {
	  public boolean ajoutLigneCommande(Personne personne,Espace espace,double quantite,double total,boolean abonneSpecial,double nbreJours);
	  public List<LigneCommande> findLigneCommandeParPersonneId(long id);
}
