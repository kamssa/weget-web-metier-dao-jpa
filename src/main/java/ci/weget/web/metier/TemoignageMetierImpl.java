package ci.weget.web.metier;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.TemoignageRepository;
import ci.weget.web.entites.ecole.Temoignage;
import ci.weget.web.entites.espace.Tarif;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class TemoignageMetierImpl implements ITemoignageMetier {
 @Autowired
 private TemoignageRepository temoignageRepository;
	@Override
	public Temoignage creer(Temoignage entity) throws InvalideTogetException {
		// TODO Auto-generated method stub
		return temoignageRepository.save(entity);
	}

	@Override
	public Temoignage modifier(Temoignage modif) throws InvalideTogetException {
		
		Optional<Temoignage> temoignage = temoignageRepository.findById(modif.getId());

		if (modif != null) {
			
			if (temoignage.get().getVersion() != modif.getVersion()) {
				throw new InvalideTogetException("ce libelle a deja ete modifier");
			}

		} else
			throw new InvalideTogetException("modif est un objet null");
		
		return temoignageRepository.save(modif);
	}

	@Override
	public List<Temoignage> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Temoignage findById(Long id) {
		
		return temoignageRepository.getByid(id);
	}

	@Override
	public boolean supprimer(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supprimer(List<Temoignage> entites) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean existe(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Temoignage> getTemoignageByIdSousBlock(Long id) {
		
		return null;
	}

}
