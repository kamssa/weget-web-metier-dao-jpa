package ci.weget.web.metier;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.CursusScolaireRepository;
import ci.weget.web.entites.abonnement.CursusColaire;
import ci.weget.web.entites.commande.Commande;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class CursusMetierImpl implements ICursusMetier {
@Autowired
CursusScolaireRepository cursusScolaireRepository;
	@Override
	public CursusColaire creer(CursusColaire entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return cursusScolaireRepository.save(entity);
	}

	@Override
	public CursusColaire modifier(CursusColaire modif) throws InvalideTogetException {
		Optional<CursusColaire> cursusColaire = cursusScolaireRepository.findById(modif.getId());

		if (modif != null) {
			
			if (cursusColaire.get().getVersion() != modif.getVersion()) {
				throw new InvalideTogetException("ce libelle a deja ete modifier");
			}

		} else
			throw new InvalideTogetException("modif est un objet null");
		
		return cursusScolaireRepository.save(modif);
	}

	@Override
	public List<CursusColaire> findAll() {
		// TODO Auto-generated method stub
		return cursusScolaireRepository.findAll();
	}

	@Override
	public CursusColaire findById(Long id) {
		// TODO Auto-generated method stub
		return cursusScolaireRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		 cursusScolaireRepository.deleteById(id);
		 return true;
	}

	@Override
	public boolean supprimer(List<CursusColaire> entites) {
		cursusScolaireRepository.deleteAll(entites);
		return true;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return cursusScolaireRepository.existsById(id);
	}

	@Override
	public List<CursusColaire> findAllCursusColaireParCv(Long id) {
		// TODO Auto-generated method stub
		return cursusScolaireRepository.findAllCursusColaireParCv(id);
	}

}
