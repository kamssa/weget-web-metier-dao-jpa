package ci.weget.web.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ci.weget.web.entites.abonnement.Document;
import ci.weget.web.entites.abonnement.Gallery;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Long> {
	// rechercher une gallery a partir de son libelle
	@Query("select ga from Gallery ga where ga.libelle=?1")
	Gallery chercherGalleryParLibelle(String libelle);

	// rechercher une gallery a partir de son id
	@Query("select ga from Gallery ga where ga.id=?1")
	Gallery chercherGalleryParId(Long id);

	
	// recupere les gallerie a partir du membre
	@Query("select ga from Gallery ga where ga.abonnement.id=?1")
	List<Gallery> findGallerieParIdAbonnement(Long id);

}
