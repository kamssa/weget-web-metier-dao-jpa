package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.DetailAbonnement;

public interface IDetailAbonnementMetier extends Imetier<DetailAbonnement, Long> {
public DetailAbonnement rechercheDetailAbonnementParId(Long id);
List<DetailAbonnement> chercherDetailAbonnementParMc(String mc);
DetailAbonnement findDetailAbonnementParIdDetailBlock(Long id);
List<DetailAbonnement> findDetailAbonnementParIdBlock(Long id);
DetailAbonnement findDetailAbonnementParNom(String nom);
List<DetailAbonnement>findDetailAbonnementParIdEta(Long id);
}
