package ci.weget.web.metier;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.GalleryRepository;
import ci.weget.web.entites.abonnement.Gallery;
import ci.weget.web.entites.ecole.Formation;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class GalleryMetierImpl implements IGalleryMetier{
	@Autowired
	private GalleryRepository galleryRepository;
	@Override
	public Gallery creer(Gallery entity) throws InvalideTogetException {
		
		return galleryRepository.save(entity);
	}

	@Override
	public Gallery modifier(Gallery modif) throws InvalideTogetException {
		
		Optional<Gallery> gallery = galleryRepository.findById(modif.getId());

		if (gallery.isPresent()) {
			
			if (gallery.get().getVersion() != modif.getVersion()) {
				throw new InvalideTogetException("ce libelle a deja ete modifier");
			}

		} else
			throw new InvalideTogetException("modif est un objet null");
		
		return galleryRepository.save(modif);
	}

	@Override
	public List<Gallery> findAll() {
		return galleryRepository.findAll();
	}

	@Override
	public Gallery findById(Long id) {
		
		return galleryRepository.chercherGalleryParId(id);
	}

	@Override
	public boolean supprimer(Long id) {
		galleryRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Gallery> entites) {
		galleryRepository.deleteAll(entites);
		return true;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Gallery chercherGalleryParLibelle(String libelle) {
		
		return galleryRepository.chercherGalleryParLibelle(libelle) ;
	}

	@Override
	public List<Gallery> findGalleryParIdDetailAbonnement(Long id) {
		
		return null;
	}

	
	@Override
	public List<Gallery> findGalleryParIdAbonnement(Long id) {
		// TODO Auto-generated method stub
		return galleryRepository.findGallerieParIdAbonnement(id);
	}

/*	@Override
	public List<PhotoGallery> pathPhotoParGalleryId(Long id) {
		
		return galleryRepository.pathPhotoParGalleryId(id);
	}*/

}
