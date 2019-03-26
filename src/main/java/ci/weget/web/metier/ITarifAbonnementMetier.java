package ci.weget.web.metier;

import ci.weget.web.entites.abonnement.TarifAbonnement;

public interface ITarifAbonnementMetier extends Imetier<TarifAbonnement, Long> {
public TarifAbonnement tarifParIdAbonnement(Long id);
}
