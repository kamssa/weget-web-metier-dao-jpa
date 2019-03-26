package ci.weget.web.metier;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ci.weget.web.dao.EspaceRepository;
import ci.weget.web.entites.espace.Espace;
import ci.weget.web.exception.InvalideTogetException;


@Service
public class EspaceMetierImpl implements IEspaceMetier {

	@Autowired
	private EspaceRepository espaceRepositry;

	@Override
	public Espace creer(Espace espace) throws InvalideTogetException {
		if ((espace.getLibelle() == null) || (espace.getLibelle() == "")) {
			throw new InvalideTogetException("Le libelle ne peut etre null");
		}
      Optional<Espace>blocks = espaceRepositry.findByLibelle(espace.getLibelle());
		if (blocks.isPresent())
			throw new InvalideTogetException("ce libelle existe deja");

		return espaceRepositry.save(espace);
	}

	@Override
	public Espace modifier(Espace modif) throws InvalideTogetException {

		Optional<Espace> espace = espaceRepositry.findById(modif.getId());

		if (espace.isPresent()) {
			
			if (espace.get().getVersion() != modif.getVersion()) {
				throw new InvalideTogetException("ce libelle a deja ete modifier");
			}

		} else
			throw new InvalideTogetException("l'objet n'existe pas");
		
		return espaceRepositry.save(modif);
	}

	@Override
	public List<Espace> getEspaceByMc(String mc) {

		return espaceRepositry.getEspaceByMc(mc);
	}

	@Override
	public List<Espace> findAll() {
		return espaceRepositry.findAll();
	}

	@Override
	public Espace findById(Long id) {
		return espaceRepositry.findById(id).get();
	}

	@Override
	public boolean supprimer(Long id) {
		espaceRepositry.deleteById(id);
		return true;
	}

	@Override
	public boolean supprimer(List<Espace> entites) {
		espaceRepositry.deleteAll(entites);
		return true;
	}

	@Override
	public boolean existe(Long id) {
		espaceRepositry.existsById(id);
		return true;
	}


	@Override
	public Espace findByLibelle(String libelle) {
		return espaceRepositry.findByLibelle(libelle).get();
	}

}
