package ci.weget.web.metier;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.AbonnementRepository;
import ci.weget.web.dao.CvAbonnementRepository;
import ci.weget.web.dao.CvPersonneRepository;
import ci.weget.web.entites.abonnement.Abonnement;
import ci.weget.web.entites.abonnement.CvAbonnement;
import ci.weget.web.entites.abonnement.CvPersonne;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class CvPersonneMetierImpl implements ICvPersonneMetier {
	@Autowired
	CvPersonneRepository cvPersonneRepository;
	@Autowired
	AbonnementRepository abonnementRepository;
	@Autowired
	CvAbonnementRepository cvAbonnementRepository;

	@Override
	public CvPersonne creer(CvPersonne entity) throws InvalideTogetException {
		CvPersonne cvPersonne = null;
		Abonnement ab = null;
		cvPersonne = cvPersonneRepository.save(entity);
		if (cvPersonne.getId().equals(null)) {
			throw new RuntimeException("Votre cv n'est pas rempli");
		}
		ab = cvPersonne.getAbonnement();
		ab.setActive(true);
		Abonnement abonne=abonnementRepository.save(ab);
		CvAbonnement cvAbonnement= new CvAbonnement();
		cvAbonnement.setIdCvPersonne(cvPersonne.getId());
		cvAbonnement.setIdAbonnement(abonne.getId());
		cvAbonnement.setSpecialite(cvPersonne.getSpecialite());
		cvAbonnementRepository.save(cvAbonnement);

		return cvPersonne;
	}

	@Override
	public CvPersonne modifier(CvPersonne modif) throws InvalideTogetException {

		Abonnement ab = null;

		Optional<CvPersonne> cvPersonne = cvPersonneRepository.findById(modif.getId());

		if (cvPersonne.isPresent()) {

			if (cvPersonne.get().getVersion() != modif.getVersion()) {
				throw new InvalideTogetException("ce libelle a deja ete modifier");
			}

		} else
			throw new InvalideTogetException("modif est un objet null");

		ab = cvPersonne.get().getAbonnement();
		ab.setActive(false);

		abonnementRepository.save(ab);
		return cvPersonneRepository.save(modif);
	}

	@Override
	public List<CvPersonne> findAll() {

		return cvPersonneRepository.findAll();
	}

	@Override
	public CvPersonne findById(Long id) {

		return cvPersonneRepository.findById(id).get();
	}

	
	@Override
	public boolean supprimer(Long id) {
		cvPersonneRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<CvPersonne> entites) {
		cvPersonneRepository.deleteAll(entites);
		return true;
	}

	@Override
	public boolean existe(Long id) {
		return cvPersonneRepository.existsById(id);
	}

	@Override
	public CvPersonne getCvPersonneByIdAbonnemnt(long id) {
		return cvPersonneRepository.getCvPersonneByIdAbonnement(id);
	}

	
}
