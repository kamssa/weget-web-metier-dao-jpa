package ci.weget.web.metier;

import ci.weget.web.entites.commande.Paiement;

public interface IPaiementMetier extends Imetier<Paiement, Long> {
Paiement getPaiementDeCommande(Long id);
}
