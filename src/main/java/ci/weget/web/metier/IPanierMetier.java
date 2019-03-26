package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.commande.Panier;
import ci.weget.web.entites.espace.Espace;
import ci.weget.web.entites.espace.Tarif;
import ci.weget.web.entites.personne.Personne;

public interface IPanierMetier extends Imetier<Panier, Long> {
  public boolean ajoutLignePanier(Tarif tarif,Espace espace,Personne personne,double quantite,double total,boolean abonneSpecial,double nbreJours);
  public boolean modifLignePanier(Long id,Tarif tarif,Personne personne,double quantite,double total,boolean abonneSpecial,double nbreJours);
  public List<Panier> getPanierByIdPersonne(long idPersonne);
}
