package ci.weget.web.metier;

import java.util.List;

import ci.weget.web.entites.abonnement.Gallery;


public interface IGalleryMetier extends Imetier<Gallery, Long>{
public Gallery chercherGalleryParLibelle(String libelle);
List<Gallery> findGalleryParIdDetailAbonnement(Long id);
List<Gallery> findGalleryParIdAbonnement(Long id);

}
