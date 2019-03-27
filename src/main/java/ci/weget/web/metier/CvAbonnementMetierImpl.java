package ci.weget.web.metier;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.AbonnementRepository;
import ci.weget.web.dao.CvAbonnementRepository;
import ci.weget.web.entites.abonnement.CvAbonnement;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class CvAbonnementMetierImpl implements ICvAbonnementMetier {
@Autowired
CvAbonnementRepository cvAbonnementRepository;
@Autowired
AbonnementRepository abonnementRepository;
	@Override
	public CvAbonnement creer(CvAbonnement entity) throws InvalideTogetException {
		return cvAbonnementRepository.save(entity);
	}

	@Override
	public CvAbonnement modifier(CvAbonnement entity) throws InvalideTogetException {
		return cvAbonnementRepository.save(entity);
	}

	@Override
	public List<CvAbonnement> findAll() {
		return cvAbonnementRepository.findAll();
	}

	@Override
	public CvAbonnement findById(Long id) {
		return cvAbonnementRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		cvAbonnementRepository.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<CvAbonnement> entites) {
		 cvAbonnementRepository.deleteAll(entites);
		 return true;
	}

	@Override
	public boolean existe(Long id) {
		cvAbonnementRepository.existsById(id);
		return true;
	}
	
}
