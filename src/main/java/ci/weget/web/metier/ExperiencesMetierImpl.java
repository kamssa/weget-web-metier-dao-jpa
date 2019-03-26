package ci.weget.web.metier;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.ExperienceRepository;
import ci.weget.web.entites.abonnement.Experiences;
import ci.weget.web.entites.espace.Espace;
import ci.weget.web.exception.InvalideTogetException;

@Service
public class ExperiencesMetierImpl implements IExperiencesMetier{
@Autowired
ExperienceRepository experienceRepository;
	@Override
	public Experiences creer(Experiences entity) throws InvalideTogetException {
		return experienceRepository.save(entity);
	}

	@Override
	public Experiences modifier(Experiences modif) throws InvalideTogetException {

		Optional<Experiences> experience = experienceRepository.findById(modif.getId());

		if (experience.isPresent()) {
			
			if (experience.get().getVersion() != modif.getVersion()) {
				throw new InvalideTogetException("ce libelle a deja ete modifier");
			}

		} else
			throw new InvalideTogetException("l'objet n'existe pas");
		
		return experienceRepository.save(modif);
	}

	@Override
	public List<Experiences> findAll() {
		return experienceRepository.findAll();
	}

	@Override
	public Experiences findById(Long id) {
		return experienceRepository.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		 experienceRepository.deleteById(id);
		 return true;
	}

	@Override
	public boolean supprimer(List<Experiences> entites) {
		return false;
	}

	@Override
	public boolean existe(Long id) {
		return false;
	}

	@Override
	public List<Experiences> findAllExperienceParCv(Long id) {
		return experienceRepository.findAllExperiencesByCv(id);
	}

}
