package ci.weget.web.metier;

import ci.weget.web.entites.commande.Commande;
import ci.weget.web.entites.personne.Personne;
import ci.weget.web.exception.InvalideTogetException;

public interface ICommandeMetier extends Imetier<Commande, Long> {
public Commande	creerCommande(Personne p,double total,String code) throws InvalideTogetException;
Commande getByIdPersonne(Long id);
public boolean creerAbonne(Personne personne) throws InvalideTogetException;

}
